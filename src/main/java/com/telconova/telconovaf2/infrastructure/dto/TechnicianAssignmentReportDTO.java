package com.telconova.telconovaf2.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianAssignmentReportDTO {
    private Integer technicianId;
    private String technicianName;
    private String specialty;
    private String zone;
    private int totalAssignments;
    private List<AssignmentSummaryDTO> assignments;
}
