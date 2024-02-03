package fr.chatop.backend.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetRentalsResponseDto {

    List<RentalDto> rentals;
}
