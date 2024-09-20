package Epicode.organizzatoreEventi.repositories;

import Epicode.organizzatoreEventi.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventiRepository extends JpaRepository<Evento, UUID> {
}
