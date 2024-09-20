package Epicode.organizzatoreEventi.entities;

import Epicode.organizzatoreEventi.enums.UtenteType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Entity
@Table(name="utenti")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Utente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private UtenteType utenteType;


    public Utente(String email, String password, UtenteType utenteType) {
        this.email = email;
        this.password = password;
        this.utenteType = utenteType;
    }

}
