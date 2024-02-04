package fr.chatop.backend.controllers;

import fr.chatop.backend.dto.SimpleStringResponseDto;
import fr.chatop.backend.dto.MessageRequestDto;
import fr.chatop.backend.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(security = @SecurityRequirement(name = "jwt"))
    @PostMapping
    public SimpleStringResponseDto createMessage(@RequestBody MessageRequestDto message) {
        messageService.createMessage(message);
        return SimpleStringResponseDto.builder()
                .message("Message sent with success")
                .build();
    }
}
