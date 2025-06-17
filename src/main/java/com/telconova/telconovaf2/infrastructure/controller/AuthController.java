package com.telconova.telconovaf2.infrastructure.controller;

import com.telconova.telconovaf2.application.service.security.AuthService;
import com.telconova.telconovaf2.infrastructure.dto.LoginRequest;
import com.telconova.telconovaf2.infrastructure.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {

        // 1 · Autentica y construye el JWT
        String token = authService.login(request.getEmail(), request.getPassword());

        // 2 · Crea la cookie con SameSite=None (imprescindible si el front está en otro dominio)
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofHours(4))
                .build();

        // 3 · Añade la cookie al header
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // 4 · 204 No Content (no hace falta devolver el token en el body si viaja en cookie)
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.noContent().build();
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

