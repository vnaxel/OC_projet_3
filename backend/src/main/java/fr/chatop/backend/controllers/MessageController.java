package fr.chatop.backend.controllers;

import fr.chatop.backend.dto.MessageDto;
import fr.chatop.backend.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public String createMessage(@RequestBody MessageDto message) {
        messageService.createMessage(message);
        return "Message sent with success!";
    }
}
