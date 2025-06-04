package com.telconova.telconovaf2.infrastructure.controller;

import com.telconova.telconovaf2.application.service.security.AuthService;
import com.telconova.telconovaf2.infrastructure.dto.LoginRequest;
import com.telconova.telconovaf2.infrastructure.dto.LoginResponse;
import com.telconova.telconovaf2.infrastructure.dto.RegisterRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, jakarta.servlet.http.HttpServletResponse response) {
        String token = authService.login(request.getEmail(), request.getPassword());

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Solo en producción con HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        cookie.setAttribute("SameSite", "Strict");// 1 hora

        response.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(
                request.getEmail(),
                request.getName(),
                request.getPassword(),
                request.getRole(),
                request.getSpecialtyId(),
                request.getZoneId()
        );
        return ResponseEntity.ok("Usuario registrado correctamente");
    }


}

