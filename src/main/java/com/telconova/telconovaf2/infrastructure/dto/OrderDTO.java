package com.telconova.telconovaf2.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Integer id;
    private String name;
    private String description;
    private double estimatedTime;
    private double workload;
    private String urgency;
    private Integer zoneId;
    private String zoneName;
    private String requester;
    private Date timestamp;
    private String technicianName;
    private String specialtyName;
}
