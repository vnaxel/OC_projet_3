package fr.chatop.backend.repositories;

import fr.chatop.backend.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
