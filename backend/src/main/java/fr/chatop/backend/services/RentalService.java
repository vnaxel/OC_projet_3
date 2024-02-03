package fr.chatop.backend.services;

import fr.chatop.backend.dto.CreateRentalRequestDto;
import fr.chatop.backend.dto.GetRentalsResponseDto;
import fr.chatop.backend.dto.RentalDto;
import fr.chatop.backend.models.Rental;
import fr.chatop.backend.models.User;
import fr.chatop.backend.repositories.UserRepository;
import fr.chatop.backend.utils.NotFoundException;
import fr.chatop.backend.repositories.RentalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalService {

    @Value("${image.upload.directory}")
    private String imageUploadDirectory;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public GetRentalsResponseDto findAll() {
        return new GetRentalsResponseDto(rentalRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList());
    }

    public RentalDto get(final Long id) {
        return rentalRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public RentalDto create(final CreateRentalRequestDto rentalRequestDto, final UserDetails userDetails) {
        final User owner = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("user not found"));
        final Rental rental = new Rental();
        rental.setName(rentalRequestDto.getName());
        rental.setDescription(rentalRequestDto.getDescription());
        rental.setPrice(rentalRequestDto.getPrice());
        rental.setSurface(rentalRequestDto.getSurface());
        rental.setOwner(owner);
        rental.setCreatedAt(LocalDateTime.now());
        final Rental savedRental = rentalRepository.save(rental);
        final String pictureUrl = saveImageLocally(savedRental, rentalRequestDto.getPicture());
        savedRental.setPictureUrl("/api/images/" + pictureUrl);
        return toDto(rentalRepository.save(savedRental));
    }

    public RentalDto update(final Long id, final RentalDto rentalDto, final UserDetails userDetails) {
        final Rental rentalToUpdate = rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("rental not found"));
        if (!rentalToUpdate.getOwner().getEmail().equals(userDetails.getUsername())) {
            throw new NotFoundException("rental owner isnt authenticated user");
        }
        final Rental updatedRental = new Rental();
        updatedRental.setId(id);
        updatedRental.setName(rentalDto.getName() != null && !rentalDto.getName().isEmpty() ? rentalDto.getName() : rentalToUpdate.getName());
        updatedRental.setDescription(rentalDto.getDescription() != null && !rentalDto.getDescription().isEmpty() ? rentalDto.getDescription() : rentalToUpdate.getDescription());
        updatedRental.setPictureUrl(rentalDto.getPicture() != null && !rentalDto.getPicture().isEmpty() ? rentalDto.getPicture() : rentalToUpdate.getPictureUrl());
        updatedRental.setPrice(rentalDto.getPrice() != null ? rentalDto.getPrice() : rentalToUpdate.getPrice());
        updatedRental.setSurface(rentalDto.getSurface() != null ? rentalDto.getSurface() : rentalToUpdate.getSurface());
        updatedRental.setOwner(rentalToUpdate.getOwner());
        updatedRental.setCreatedAt(rentalToUpdate.getCreatedAt());
        updatedRental.setUpdatedAt(LocalDateTime.now());
        return toDto(rentalRepository.save(updatedRental));
    }

    public void delete(final Long id, final UserDetails userDetails) {
        final Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("rental not found"));
        if (!rental.getOwner().getEmail().equals(userDetails.getUsername())) {
            throw new NotFoundException("rental not found");
        }
        rentalRepository.delete(rental);
    }


    private RentalDto toDto(final Rental rental) {
        return RentalDto.builder()
                .id(rental.getId())
                .name(rental.getName())
                .description(rental.getDescription())
                .picture(rental.getPictureUrl())
                .price(rental.getPrice())
                .surface(rental.getSurface())
                .owner_id(rental.getOwner().getId())
                .created_at(rental.getCreatedAt())
                .updated_at(rental.getUpdatedAt())
                .build();
    }

    public String saveImageLocally(Rental rental, MultipartFile file) {

        final String fileName = rental.getId() + "_" + file.getOriginalFilename();
        final Path filePath = Path.of(imageUploadDirectory + "/" + fileName);
        //si l'image existe deja, on la supprime
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                log.error("Error while deleting image", e);
            }
        }
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            log.error("Error while saving image", e);
        }
        return fileName;
    }
}
