package net.enjoy.springboot.registrationlogin.service;

import net.enjoy.springboot.registrationlogin.dto.UserDto;
import net.enjoy.springboot.registrationlogin.entity.User;

import java.util.List;

import lombok.RequiredArgsConstructor;
import net.enjoy.springboot.registrationlogin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public String register(String username, String password) {

        System.out.println("username "+username +" password "+password);

        if (userRepository.findByUsername(username).isPresent()) {
            return "Username already exists";
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(String username, String password) {

        return userRepository.findByUsername(username)
                .map(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        return "Login successful";
                    } else {
                        return "Invalid credentials";
                    }
                })
                .orElse("User not found");
    }
}
