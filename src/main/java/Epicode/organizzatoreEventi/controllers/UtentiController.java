package Epicode.organizzatoreEventi.controllers;

import Epicode.organizzatoreEventi.entities.Prenotazione;
import Epicode.organizzatoreEventi.entities.Utente;
import Epicode.organizzatoreEventi.recordsDTO.UtenteDTO;
import Epicode.organizzatoreEventi.services.EventiService;
import Epicode.organizzatoreEventi.services.PrenotazioniService;
import Epicode.organizzatoreEventi.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UtentiController {
    @Autowired
    UtentiService utentiService;
    @Autowired
    EventiService eventiService;
    @Autowired
    PrenotazioniService prenotazioniService;

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return currentAuthenticatedUtente;
    }


    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        this.utentiService.findAndDelete(currentAuthenticatedUtente.getId());
    }


    @PutMapping("/me")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody @Validated UtenteDTO utenteDTO) {
        return this.utentiService.update(currentAuthenticatedUtente.getId(), utenteDTO);
    }

    @GetMapping("/me/prenotazioni")
    public List<Prenotazione> getPrenotazioni(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return this.prenotazioniService.findPrenotazioni(currentAuthenticatedUtente.getId());
    }

}
