package fr.chatop.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RentalDto {

    private Long id;
    private String name;
    @Size(max = 2000)
    private String description;
    private String pictureURL;
    private Integer price;
    private Integer surface;
    private Long owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
