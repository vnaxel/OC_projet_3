package fr.chatop.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class CreateRentalRequestDto {

        private String name;
        private String description;
        private MultipartFile picture;
        private Integer price;
        private Integer surface;
        private Long owner;
}
