package Epicode.organizzatoreEventi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "eventi")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Evento {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String titolo;
    private String descrizione;
    private LocalDate dataEvento;
    private String luogo;
    private Integer nPostidisponibili;
    @ManyToOne
    @JoinColumn(name = "organizzatore_id")
    private Utente organizzatore;

    public Evento(String titolo, String descrizione, LocalDate dataEvento, String luogo, Integer nPostidisponibili, Utente organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataEvento = dataEvento;
        this.luogo = luogo;
        this.nPostidisponibili = nPostidisponibili;
        this.organizzatore = organizzatore;
    }
}