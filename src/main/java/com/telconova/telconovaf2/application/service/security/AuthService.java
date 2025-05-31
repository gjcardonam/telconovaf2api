package com.telconova.telconovaf2.application.service.security;

import com.telconova.telconovaf2.domain.entities.Role;
import com.telconova.telconovaf2.domain.entities.User;
import com.telconova.telconovaf2.domain.repository.UserRepository;
import com.telconova.telconovaf2.infrastructure.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return jwtUtils.generateToken(user);
    }

    public void register(String email, String name, String rawPassword, String role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(rawPassword)); // Aquí se codifica la contraseña
        user.setRole(Role.valueOf(role.toUpperCase()));

        userRepository.save(user);
    }

}

