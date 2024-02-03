package fr.chatop.backend.controllers;

import fr.chatop.backend.dto.CreateOrUpdateRentalResponseDto;
import fr.chatop.backend.dto.CreateRentalRequestDto;
import fr.chatop.backend.dto.GetRentalsResponseDto;
import fr.chatop.backend.dto.RentalDto;
import fr.chatop.backend.services.RentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Slf4j
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    public GetRentalsResponseDto findAll() {
        return rentalService.findAll();
    }

    @GetMapping("/{id}")
    public RentalDto findById(@PathVariable Long id) {
        return rentalService.get(id);
    }

    @PostMapping
    public CreateOrUpdateRentalResponseDto save(@RequestParam("name") String name,
                                                @RequestParam("description") String description,
                                                @RequestParam("picture") MultipartFile picture,
                                                @RequestParam("price") Integer price,
                                                @RequestParam("surface") Integer surface,
                                                @AuthenticationPrincipal UserDetails userDetails) {

        CreateRentalRequestDto rentalRequestDto = CreateRentalRequestDto.builder()
                .name(name)
                .description(description)
                .picture(picture)
                .price(price)
                .surface(surface)
                .build();

        rentalService.create(rentalRequestDto, userDetails);

        return CreateOrUpdateRentalResponseDto.builder()
                .message("Rental created !")
                .build();
    }

    @PutMapping("/{id}")
    public CreateOrUpdateRentalResponseDto update(@PathVariable Long id,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("price") Integer price,
                                                  @RequestParam("surface") Integer surface,
                                                  @AuthenticationPrincipal UserDetails userDetails) {

        RentalDto rentalDto = RentalDto.builder()
                .name(name)
                .description(description)
                .price(price)
                .surface(surface)
                .build();

        rentalService.update(id, rentalDto, userDetails);
        return CreateOrUpdateRentalResponseDto.builder()
                .message("Rental updated !")
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        rentalService.delete(id, userDetails);
    }
}
