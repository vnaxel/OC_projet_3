package fr.chatop.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class CreateOrUpdateRentalResponseDto {

    private String message;
}
