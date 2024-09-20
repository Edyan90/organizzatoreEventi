package Epicode.organizzatoreEventi.repositories;

import Epicode.organizzatoreEventi.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {
    @Query("SELECT pr FROM Prenotazione pr WHERE pr.utente.id=:utenteID")
    List<Prenotazione> findPrenotazioniByID(UUID utenteID);
}
