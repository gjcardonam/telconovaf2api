package com.telconova.telconovaf2.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {

    private Integer id;
    private Integer technicianId;
    private String technicianName;
    private Integer orderId;
    private String orderName;
    private Date timestamp;
}
