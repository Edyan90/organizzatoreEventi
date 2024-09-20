package Epicode.organizzatoreEventi.controllers;

import Epicode.organizzatoreEventi.entities.Prenotazione;
import Epicode.organizzatoreEventi.exceptions.BadRequestEx;
import Epicode.organizzatoreEventi.recordsDTO.PrenotazioneDTO;
import Epicode.organizzatoreEventi.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    @Autowired
    PrenotazioniService prenotazioniService;

    @GetMapping
    public Page<Prenotazione> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataDiRichiesta") String sortBy) {
        return this.prenotazioniService.findAll(page, size, sortBy);
    }

    @GetMapping("{prenotazioneID}")
    public Prenotazione findByID(@PathVariable UUID prenotazioneID) {
        return this.prenotazioniService.findByID(prenotazioneID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione created(@RequestBody @Validated PrenotazioneDTO prenotazioneDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestEx("Ci sono stati errori nel payload: " + messages);
        } else {
            Prenotazione prenotazione = this.prenotazioniService.save(prenotazioneDTO);
            return prenotazione;
        }
    }

    @PutMapping("{prenotazioneID}")
    public Prenotazione update(@PathVariable UUID prenotazioneID, @RequestBody PrenotazioneDTO prenotazioneDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestEx("Ci sono stati errori nel payload: " + messages);
        } else {
            return this.prenotazioniService.updatePrenotazione(prenotazioneDTO, prenotazioneID);
        }
    }

    @DeleteMapping("{prenotazioneID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable UUID prenotazioneID) {
        this.prenotazioniService.findAndDelete(prenotazioneID);
    }

}
