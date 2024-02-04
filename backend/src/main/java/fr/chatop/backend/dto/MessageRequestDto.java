package fr.chatop.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MessageRequestDto {
    private String message;
    private Long user_id;
    private Long rental_id;
}
