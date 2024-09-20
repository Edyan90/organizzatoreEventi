package Epicode.organizzatoreEventi.recordsDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventoDTO(
        @NotEmpty(message = "il titolo è obbligatorio!")
        @Size(min = 3, max = 20, message = "il titolo deve avere un minimo di 2 ad un massimo di 20 caratteri")
        String titolo,
        @NotEmpty(message = "la descrizione è obbligatoria!")
        @Size(min = 3, max = 20, message = "la descrizione deve avere un minimo di 2 ad un massimo di 20 caratteri")
        String descrizione,
        @NotNull(message = "manca data del evento o hai sbagliato formato YYYY-MM-DD")
        LocalDate data,
        @NotEmpty(message = "il luogo è obbligatorio!")
        @Size(min = 3, max = 20, message = "il luogo deve avere un minimo di 2 ad un massimo di 20 caratteri")
        String luogo,
        @NotNull(message = "il numero di posti disponibili è obbligatoria!")
        Integer numerodiPosti,
        @NotEmpty(message = "l'id del organizzatore  è obbligatorio!")
        String organizzatoreID
) {
}
