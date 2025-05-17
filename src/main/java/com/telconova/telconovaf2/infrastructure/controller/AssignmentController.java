package com.telconova.telconovaf2.infrastructure.controller;

import com.telconova.telconovaf2.domain.controller.AssignmentControlerI;
import com.telconova.telconovaf2.domain.service.AssignmentServiceI;
import com.telconova.telconovaf2.infrastructure.dto.AssignmentDTO;
import com.telconova.telconovaf2.infrastructure.dto.TechnicianAssignmentReportDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController implements AssignmentControlerI {

    private final AssignmentServiceI assignmentService;

    public AssignmentController(AssignmentServiceI assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<AssignmentDTO> assignOrderToTechnician(@RequestBody AssignmentDTO assignmentDTO) {
        return ResponseEntity.ok(assignmentService.assignOrderToTechnician(assignmentDTO));
    }

    @PostMapping("/auto")
    public ResponseEntity<AssignmentDTO> autoAssign(@RequestParam Integer orderId) {
        AssignmentDTO assigned = assignmentService.assignAutomatically(orderId);
        return ResponseEntity.ok(assigned);
    }

    @GetMapping("/report")
    public ResponseEntity<List<TechnicianAssignmentReportDTO>> getReport() {
        return ResponseEntity.ok(assignmentService.getAssignmentReport());
    }
}
