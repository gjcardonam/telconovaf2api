package com.telconova.telconovaf2.infrastructure.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<String, HttpStatus> errorMapping = Map.of(
            "Usuario no encontrado", HttpStatus.NOT_FOUND,
            "Credenciales inválidas", HttpStatus.UNAUTHORIZED,
            "El correo ya está registrado", HttpStatus.CONFLICT,
            "Specialty no encontrada", HttpStatus.BAD_REQUEST,
            "Zone no encontrada", HttpStatus.BAD_REQUEST,
            "Orden no encontrada", HttpStatus.NOT_FOUND,
            "Técnico no encontrado", HttpStatus.NOT_FOUND
    );

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        HttpStatus status = errorMapping.getOrDefault(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(status).body(ex.getMessage());
    }
}