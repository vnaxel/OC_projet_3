package fr.learn.springsecurity6authserver.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    Integer surface;

    Integer price;

    String pictureUrl;

    String description;

    @ManyToOne
    @JoinColumn(name="owner_id", referencedColumnName = "id")
    User owner;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
