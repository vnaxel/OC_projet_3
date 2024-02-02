package fr.learn.springsecurity6authserver.repositories;

import fr.learn.springsecurity6authserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByEmail(String email);
}
