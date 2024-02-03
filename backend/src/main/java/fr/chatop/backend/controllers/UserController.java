package fr.chatop.backend.controllers;

import fr.chatop.backend.dto.UserDto;
import fr.chatop.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(security = @SecurityRequirement(name = "jwt"))
    @GetMapping("{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.get(id);
    }
}
