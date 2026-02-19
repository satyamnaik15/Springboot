package net.enjoy.springboot.registrationlogin.controller;

import lombok.RequiredArgsConstructor;
import net.enjoy.springboot.registrationlogin.dto.AuthResponse;
import net.enjoy.springboot.registrationlogin.dto.LoginRequest;
import net.enjoy.springboot.registrationlogin.dto.RegisterRequest;
import net.enjoy.springboot.registrationlogin.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}

