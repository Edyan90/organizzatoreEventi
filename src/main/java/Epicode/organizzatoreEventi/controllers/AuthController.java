package Epicode.organizzatoreEventi.controllers;

import Epicode.organizzatoreEventi.exceptions.BadRequestEx;
import Epicode.organizzatoreEventi.recordsDTO.LoginDTO;
import Epicode.organizzatoreEventi.recordsDTO.UtenteDTO;
import Epicode.organizzatoreEventi.recordsDTO.UtenteRespDTO;
import Epicode.organizzatoreEventi.services.AuthService;
import Epicode.organizzatoreEventi.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginDTO loginDTO) {
        return this.authService.checkCredenzialiAndGeneraToken(loginDTO);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteRespDTO createUtente(@RequestBody @Validated UtenteDTO utenteDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(", "));
            throw new BadRequestEx("Ci sono stati errori nel payload " + messages);
        } else {
            return new UtenteRespDTO(this.utentiService.save(utenteDTO).getId());
        }
    }
}
