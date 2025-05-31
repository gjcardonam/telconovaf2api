package com.telconova.telconovaf2.infrastructure.controller;

import com.telconova.telconovaf2.application.service.security.AuthService;
import com.telconova.telconovaf2.infrastructure.dto.LoginRequest;
import com.telconova.telconovaf2.infrastructure.dto.LoginResponse;
import com.telconova.telconovaf2.infrastructure.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request.getEmail(), request.getName(), request.getPassword(), request.getRole());
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

}

