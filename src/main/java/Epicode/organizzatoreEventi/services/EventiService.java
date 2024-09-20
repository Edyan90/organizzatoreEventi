package Epicode.organizzatoreEventi.services;

import Epicode.organizzatoreEventi.entities.Evento;
import Epicode.organizzatoreEventi.entities.Utente;
import Epicode.organizzatoreEventi.exceptions.BadRequestEx;
import Epicode.organizzatoreEventi.exceptions.NotFoundEx;
import Epicode.organizzatoreEventi.recordsDTO.EventoDTO;
import Epicode.organizzatoreEventi.repositories.EventiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class EventiService {
    @Autowired
    EventiRepository eventiRepository;
    @Autowired
    UtentiService utentiService;

    public Page<Evento> findAll(int page, int size, String sortBy) {
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventiRepository.findAll(pageable);
    }

    public Evento findByID(UUID eventoID) {
        return this.eventiRepository.findById(eventoID).orElseThrow(() -> new NotFoundEx(eventoID));
    }

    public Evento save(EventoDTO eventoDTO) {
        if (eventoDTO.data().isBefore(LocalDate.now())) {
            throw new BadRequestEx("la data del evento non può essere precedente alla data di oggi");
        }
        Utente utente = this.utentiService.findByID(UUID.fromString(eventoDTO.organizzatoreID()));
        Evento evento = new Evento(
                eventoDTO.titolo(),
                eventoDTO.descrizione(),
                eventoDTO.data(),
                eventoDTO.luogo(),
                eventoDTO.numerodiPosti(),
                utente);
        return this.eventiRepository.save(evento);
    }

    public Evento updateEvento(UUID eventoID, EventoDTO eventoDTO) {
        Evento evento = this.findByID(eventoID);
        evento.setTitolo(eventoDTO.titolo());
        evento.setDescrizione(eventoDTO.descrizione());
        evento.setDataEvento(eventoDTO.data());
        evento.setLuogo(eventoDTO.luogo());
        evento.setNPostidisponibili(eventoDTO.numerodiPosti());
        evento.setOrganizzatore(this.utentiService.findByID(UUID.fromString(eventoDTO.organizzatoreID())));
        this.eventiRepository.save(evento);
        return evento;
    }

    public void findByIDAndDelete(UUID eventoID) {
        Evento evento = this.findByID(eventoID);
        this.eventiRepository.delete(evento);
    }
}