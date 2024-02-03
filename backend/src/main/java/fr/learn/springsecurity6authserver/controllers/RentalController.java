package fr.learn.springsecurity6authserver.controllers;

import fr.learn.springsecurity6authserver.dto.RentalDto;
import fr.learn.springsecurity6authserver.services.RentalService;
import fr.learn.springsecurity6authserver.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Slf4j
public class RentalController {

        private final RentalService rentalService;

        @GetMapping
        public List<RentalDto> findAll() {
            return rentalService.findAll();
        }

        @GetMapping("/{id}")
        public RentalDto findById(@PathVariable Long id) {
            return rentalService.get(id);
        }

        @PostMapping
        public RentalDto save(@RequestBody RentalDto rentalDto, @AuthenticationPrincipal UserDetails userDetails) {
            return rentalService.create(rentalDto, userDetails);
        }

        @PutMapping("/{id}")
        public RentalDto update(@PathVariable Long id, @RequestBody RentalDto rentalDto, @AuthenticationPrincipal UserDetails userDetails) {
            return rentalService.update(id, rentalDto, userDetails);
        }

        @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
            rentalService.delete(id, userDetails);
        }
}
