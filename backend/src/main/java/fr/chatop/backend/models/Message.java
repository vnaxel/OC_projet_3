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
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Rental rental;

    @ManyToOne
    private User sender;

    private String message;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
