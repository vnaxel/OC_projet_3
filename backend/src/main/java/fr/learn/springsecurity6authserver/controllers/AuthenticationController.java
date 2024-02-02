package fr.learn.springsecurity6authserver.controllers;

import fr.learn.springsecurity6authserver.dto.JwtAuthenticationResponse;
import fr.learn.springsecurity6authserver.dto.LoginRequest;
import fr.learn.springsecurity6authserver.dto.RegisterRequest;
import fr.learn.springsecurity6authserver.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public JwtAuthenticationResponse register(@RequestBody RegisterRequest request) {
        return  authenticationService.register(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }
}
