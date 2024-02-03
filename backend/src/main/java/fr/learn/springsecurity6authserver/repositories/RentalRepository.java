package fr.learn.springsecurity6authserver.repositories;

import fr.learn.springsecurity6authserver.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
