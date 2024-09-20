package Epicode.organizzatoreEventi.controllers;

import Epicode.organizzatoreEventi.entities.Evento;
import Epicode.organizzatoreEventi.exceptions.BadRequestEx;
import Epicode.organizzatoreEventi.recordsDTO.EventoDTO;
import Epicode.organizzatoreEventi.services.EventiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventi")
public class EventiController {
    @Autowired
    private EventiService eventiService;

    @GetMapping
    public Page<Evento> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "dataEvento") String sortBy) {
        return this.eventiService.findAll(page, size, sortBy);
    }

    @GetMapping("{eventoID}")
    public Evento findByID(@PathVariable UUID eventoID) {
        return this.eventiService.findByID(eventoID);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento create(@RequestBody @Validated EventoDTO eventoDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestEx("Ci sono stati errori nel payload: " + messages);
        } else {
            return this.eventiService.save(eventoDTO);
        }
    }


    @PutMapping("{prenotazioneID}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento update(@PathVariable UUID eventoID, @RequestBody EventoDTO eventoDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestEx("Ci sono stati errori nel payload: " + messages);
        } else {
            return this.eventiService.updateEvento(eventoID, eventoDTO);
        }
    }

    @DeleteMapping("{eventoID}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable UUID eventoID) {
        this.eventiService.findByIDAndDelete(eventoID);
    }
}
