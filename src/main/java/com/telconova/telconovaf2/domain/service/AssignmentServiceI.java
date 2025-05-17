package com.telconova.telconovaf2.domain.service;

import com.telconova.telconovaf2.infrastructure.dto.AssignmentDTO;
import com.telconova.telconovaf2.infrastructure.dto.TechnicianAssignmentReportDTO;

import java.util.List;

public interface AssignmentServiceI {
    AssignmentDTO assignOrderToTechnician(AssignmentDTO AssignmentDTO);
    AssignmentDTO assignAutomatically(Integer orderId);
    List<TechnicianAssignmentReportDTO> getAssignmentReport();
}
