package fr.chatop.backend.models;

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
    private Long id;

    private String name;

    private Integer surface;

    private Integer price;

    private String pictureUrl;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name="owner_id", referencedColumnName = "id")
    private User owner;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
