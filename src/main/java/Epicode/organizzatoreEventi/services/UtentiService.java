package Epicode.organizzatoreEventi.services;

import Epicode.organizzatoreEventi.entities.Utente;
import Epicode.organizzatoreEventi.enums.UtenteType;
import Epicode.organizzatoreEventi.exceptions.BadRequestEx;
import Epicode.organizzatoreEventi.exceptions.NotFoundEx;
import Epicode.organizzatoreEventi.recordsDTO.UtenteDTO;
import Epicode.organizzatoreEventi.repositories.UtentiRepository;
import Epicode.organizzatoreEventi.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtentiService {

    @Autowired
    MailgunSender mailgunSender;
    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utentiRepository.findAll(pageable);
    }

    public Utente findByID(UUID utenteID) {
        return this.utentiRepository.findById(utenteID).orElseThrow(() -> new NotFoundEx(utenteID));
    }

    public Utente save(UtenteDTO utenteDTO) {
        this.utentiRepository.findByEmail(utenteDTO.email()).ifPresent(dipendente -> {
            throw new BadRequestEx("L'email " + utenteDTO.email() + " è già in uso!");
        });

        Utente utente = new Utente(
                utenteDTO.email(),
                bcrypt.encode(utenteDTO.password())
        );

        switch (utenteDTO.ruolo().toLowerCase()) {
            case "partecipante":
                utente.setUtenteType(UtenteType.PARTECIPANTE);
                break;
            case "organizzatore":
                utente.setUtenteType(UtenteType.ORGANIZZATORE);
                break;

            default:
                throw new BadRequestEx("Stato non valido: " + utenteDTO.ruolo() +
                        ". I valori validi sono: PARTECIPANTE E ORGANIZZATORE.");
        }

        mailgunSender.sendRegistrationEmail(utente);
        return utente;
    }

    public Utente findByEmail(String email) {
        return this.utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundEx(email));
    }
}
