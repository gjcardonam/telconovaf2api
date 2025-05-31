package com.telconova.telconovaf2.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    // Getters y setters
    private String email;
    private String name;
    private String password;
    private String role;

}
