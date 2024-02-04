package fr.chatop.backend.controllers;

import fr.chatop.backend.dto.CreateRentalRequestDto;
import fr.chatop.backend.dto.GetRentalsResponseDto;
import fr.chatop.backend.dto.RentalDto;
import fr.chatop.backend.dto.SimpleStringResponseDto;
import fr.chatop.backend.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(security = @SecurityRequirement(name = "jwt"))
    @GetMapping
    public GetRentalsResponseDto findAll() {
        return rentalService.findAll();
    }

    @Operation(security = @SecurityRequirement(name = "jwt"))
    @GetMapping("/{id}")
    public RentalDto findById(@PathVariable Long id) {
        return rentalService.get(id);
    }

    @Operation(security = @SecurityRequirement(name = "jwt"))
    @PostMapping
    public SimpleStringResponseDto save(@RequestParam("name") String name,
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

        return SimpleStringResponseDto.builder()
                .message("Rental created !")
                .build();
    }

    @Operation(security = @SecurityRequirement(name = "jwt"))
    @PutMapping("/{id}")
    public SimpleStringResponseDto update(@PathVariable Long id,
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
        return SimpleStringResponseDto.builder()
                .message("Rental updated !")
                .build();
    }

    @Operation(security = @SecurityRequirement(name = "jwt"))
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        rentalService.delete(id, userDetails);
    }
}
