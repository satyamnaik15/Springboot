package net.enjoy.springboot.registrationlogin.service;

import lombok.RequiredArgsConstructor;
import net.enjoy.springboot.registrationlogin.dto.AuthResponse;
import net.enjoy.springboot.registrationlogin.dto.LoginRequest;
import net.enjoy.springboot.registrationlogin.dto.RegisterRequest;
import net.enjoy.springboot.registrationlogin.entity.User;
import net.enjoy.springboot.registrationlogin.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}

