package fr.chatop.backend.controllers;

import fr.chatop.backend.dto.JwtAuthenticationResponseDto;
import fr.chatop.backend.dto.LoginRequestDto;
import fr.chatop.backend.dto.RegisterRequestDto;
import fr.chatop.backend.dto.UserDto;
import fr.chatop.backend.services.AuthenticationService;
import fr.chatop.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public JwtAuthenticationResponseDto register(@RequestBody RegisterRequestDto request) {
        return  authenticationService.register(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponseDto login(@RequestBody LoginRequestDto request) {
        return authenticationService.login(request);
    }

    @GetMapping("/me")
    public UserDto me(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getAuthenticatedUser(userDetails);
    }
}
