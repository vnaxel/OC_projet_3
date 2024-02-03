package fr.chatop.backend.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateMessageResponseDto {

    private String message;

}
