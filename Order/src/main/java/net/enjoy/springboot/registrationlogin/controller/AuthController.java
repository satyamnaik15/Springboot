package net.enjoy.springboot.registrationlogin.controller;

import lombok.RequiredArgsConstructor;
import net.enjoy.springboot.registrationlogin.dto.AuthResponse;
import net.enjoy.springboot.registrationlogin.dto.LoginRequest;
import net.enjoy.springboot.registrationlogin.dto.RegisterRequest;
import net.enjoy.springboot.registrationlogin.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        log.info("Register request received for username: {}", request.getUsername());
        authService.register(request);
        log.info("User registered successfully: {}", request.getUsername());
        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        log.info("Login request received for username: {}", request.getUsername());
        AuthResponse response = authService.login(request);
        log.info("Login successful for username: {}", request.getUsername());
        return response;
    }
}

