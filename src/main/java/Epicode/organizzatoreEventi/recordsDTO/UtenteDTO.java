package Epicode.organizzatoreEventi.recordsDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UtenteDTO(
        @NotEmpty(message = "l'email è obbligatorio!")
        @Size(min = 3, max = 30, message = "l'email deve avere un minimo di 2 ad un massimo di 20 caratteri")
        String email,
        @NotEmpty(message = "la password è obbligatoria!")
        @Size(min = 3, max = 30, message = "l'email deve avere un minimo di 2 ad un massimo di 20 caratteri")
        String password,
        @NotEmpty(message = "il ruolo è obbligatorio!")
        @Size(min = 3, max = 30, message = "i ruoli disponibili sono PARTECIPANTE E ORGANIZZATORE")
        String ruolo
) {
}
