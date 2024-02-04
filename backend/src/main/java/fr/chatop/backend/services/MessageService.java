package fr.chatop.backend.services;

import fr.chatop.backend.dto.MessageRequestDto;
import fr.chatop.backend.models.Message;
import fr.chatop.backend.repositories.MessageRepository;
import fr.chatop.backend.repositories.RentalRepository;
import fr.chatop.backend.repositories.UserRepository;
import fr.chatop.backend.utils.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public void createMessage(MessageRequestDto message) {

        messageRepository.save(Message.builder()
                .message(message.getMessage())
                .sender(userRepository.findById(message.getUser_id())
                        .orElseThrow(() -> new NotFoundException("user not found")))
                .rental(rentalRepository.findById(message.getRental_id())
                        .orElseThrow(() -> new NotFoundException("rental not found")))
                .build());
    }
}
