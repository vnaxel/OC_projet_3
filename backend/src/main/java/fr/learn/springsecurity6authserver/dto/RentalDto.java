package fr.learn.springsecurity6authserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RentalDto {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer price;
    private Integer surface;
    private Long owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
