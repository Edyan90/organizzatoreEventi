package Epicode.organizzatoreEventi.services;


import Epicode.organizzatoreEventi.entities.Utente;
import Epicode.organizzatoreEventi.exceptions.UnauthorizedEx;
import Epicode.organizzatoreEventi.recordsDTO.LoginDTO;
import Epicode.organizzatoreEventi.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredenzialiAndGeneraToken(LoginDTO loginDTO) {
        Utente utente = utentiService.findByEmail(loginDTO.email());
        if (bcrypt.matches(loginDTO.password(), utente.getPassword())) {
            return jwtTools.createToken(utente);
        } else {
            throw new UnauthorizedEx("Credenziali errate!");
        }
    }
}
