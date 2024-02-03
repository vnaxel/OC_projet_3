package fr.chatop.backend.repositories;

import fr.chatop.backend.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
