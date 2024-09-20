package Epicode.organizzatoreEventi.recordsDTO;

import jakarta.validation.constraints.NotNull;

public record PrenotazioneDTO(
        @NotNull(message = "manca l'id del evento!")
        String eventoID,
        @NotNull(message = "manca l'id del utente!")
        String utenteID
) {
}
