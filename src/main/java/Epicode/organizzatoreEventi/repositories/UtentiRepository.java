package Epicode.organizzatoreEventi.repositories;

import Epicode.organizzatoreEventi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, UUID> {
    Optional<Utente> findByEmail(String email);

    boolean existsByEmail(String email);
}
