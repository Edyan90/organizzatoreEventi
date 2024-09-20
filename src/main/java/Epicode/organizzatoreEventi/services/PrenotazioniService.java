package Epicode.organizzatoreEventi.services;


import Epicode.organizzatoreEventi.entities.Evento;
import Epicode.organizzatoreEventi.entities.Prenotazione;
import Epicode.organizzatoreEventi.entities.Utente;
import Epicode.organizzatoreEventi.exceptions.BadRequestEx;
import Epicode.organizzatoreEventi.exceptions.NotFoundEx;
import Epicode.organizzatoreEventi.recordsDTO.PrenotazioneDTO;
import Epicode.organizzatoreEventi.repositories.PrenotazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioniService {
    @Autowired
    private PrenotazioniRepository prenotazioniRepository;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private EventiService eventiService;

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioniRepository.findAll(pageable);
    }

    public List<Prenotazione> findPrenotazioni(UUID utenteID) {
        return this.prenotazioniRepository.findPrenotazioniByID(utenteID);
    }

    public Prenotazione findByID(UUID prenotazioneID) {
        return this.prenotazioniRepository.findById(prenotazioneID).orElseThrow(() -> new NotFoundEx(prenotazioneID));
    }

    public Prenotazione save(PrenotazioneDTO prenotazioneDTO) {
        Utente utente = this.utentiService.findByID(UUID.fromString(prenotazioneDTO.utenteID()));
        Evento evento = this.eventiService.findByID(UUID.fromString(prenotazioneDTO.eventoID()));
        Prenotazione prenotazione = new Prenotazione(evento, utente);
        try {
            return this.prenotazioniRepository.save(prenotazione);

        } catch (Exception e) {
            throw new BadRequestEx("Errore nel salvataggio della prenotazione. Verifica i dati e riprova.");
        }
    }

    public Prenotazione updatePrenotazione(PrenotazioneDTO prenotazioneDTO, UUID prenotazioneID) {

        Prenotazione prenotazione = this.findByID(prenotazioneID);
        Utente utente = this.utentiService.findByID(UUID.fromString(prenotazioneDTO.utenteID()));
        Evento evento = this.eventiService.findByID(UUID.fromString(prenotazioneDTO.eventoID()));
        prenotazione.setEvento(evento);
        prenotazione.setUtente(utente);
        return this.prenotazioniRepository.save(prenotazione);
    }

    public void findAndDelete(UUID prenotazioneID) {
        Prenotazione prenotazione = this.findByID(prenotazioneID);
        this.prenotazioniRepository.delete(prenotazione);
    }
}
