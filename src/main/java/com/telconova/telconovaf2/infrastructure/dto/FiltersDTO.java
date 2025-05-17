package com.telconova.telconovaf2.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltersDTO {

    private String zoneName;
    private String specialtyName;
    private String supervisorName;
    private String technicianName;
}
