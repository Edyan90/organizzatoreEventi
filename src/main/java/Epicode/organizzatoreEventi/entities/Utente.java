package Epicode.organizzatoreEventi.entities;

import Epicode.organizzatoreEventi.enums.UtenteType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utenti")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private UtenteType utenteType;
    @JsonIgnore
    @OneToMany(mappedBy = "organizzatore")
    private List<Evento> eventoList;
    @JsonIgnore
    @OneToMany(mappedBy = "utente")
    private List<Prenotazione> prenotazioneList;

    public Utente(String email, String password) {
        this.email = email;
        this.password = password;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.utenteType.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
