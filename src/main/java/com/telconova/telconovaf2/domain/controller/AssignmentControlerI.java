package com.telconova.telconovaf2.domain.controller;

import com.telconova.telconovaf2.infrastructure.dto.AssignmentDTO;
import org.springframework.http.ResponseEntity;

public interface AssignmentControlerI {
    ResponseEntity<AssignmentDTO> assignOrderToTechnician(AssignmentDTO assignmentDTO);
}
