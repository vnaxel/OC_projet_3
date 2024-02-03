package fr.learn.springsecurity6authserver.services;

import fr.learn.springsecurity6authserver.dto.RentalDto;
import fr.learn.springsecurity6authserver.models.Rental;
import fr.learn.springsecurity6authserver.models.User;
import fr.learn.springsecurity6authserver.repositories.RentalRepository;
import fr.learn.springsecurity6authserver.repositories.UserRepository;
import fr.learn.springsecurity6authserver.utils.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public List<RentalDto> findAll() {
        return rentalRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public RentalDto get(final Long id) {
        return rentalRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public RentalDto create(final RentalDto rentalDto, final UserDetails userDetails) {
        final Rental rental = new Rental();
        rental.setName(rentalDto.getName());
        rental.setDescription(rentalDto.getDescription());
        rental.setPictureUrl(rentalDto.getImageUrl());
        rental.setPrice(rentalDto.getPrice());
        rental.setSurface(rentalDto.getSurface());
        final User owner = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("owner not found"));
        rental.setOwner(owner);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());
        return toDto(rentalRepository.save(rental));
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
        updatedRental.setPictureUrl(rentalDto.getImageUrl() != null && !rentalDto.getImageUrl().isEmpty() ? rentalDto.getImageUrl() : rentalToUpdate.getPictureUrl());
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
                .imageUrl(rental.getPictureUrl())
                .price(rental.getPrice())
                .surface(rental.getSurface())
                .owner(rental.getOwner() == null ? null : rental.getOwner().getId())
                .createdAt(rental.getCreatedAt())
                .updatedAt(rental.getUpdatedAt())
                .build();
    }
}
