package com.telconova.telconovaf2.infrastructure.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String name;
    private String password;
    private String role;
    private Integer specialtyId;
    private Integer zoneId;
}
