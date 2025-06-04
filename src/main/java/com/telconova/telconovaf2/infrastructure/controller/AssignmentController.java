package com.telconova.telconovaf2.infrastructure.controller;

import com.telconova.telconovaf2.domain.service.AssignmentServiceI;
import com.telconova.telconovaf2.infrastructure.assembler.AssignmentModelAssembler;
import com.telconova.telconovaf2.infrastructure.assembler.TechnicianAssignmentReportAssembler;
import com.telconova.telconovaf2.infrastructure.dto.AssignmentDTO;
import com.telconova.telconovaf2.infrastructure.dto.TechnicianAssignmentReportDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final AssignmentServiceI assignmentService;
    private final AssignmentModelAssembler assignmentAssembler;
    private final TechnicianAssignmentReportAssembler reportAssembler;

    public AssignmentController(AssignmentServiceI assignmentService,
                                AssignmentModelAssembler assignmentAssembler,
                                TechnicianAssignmentReportAssembler reportAssembler) {
        this.assignmentService = assignmentService;
        this.assignmentAssembler = assignmentAssembler;
        this.reportAssembler = reportAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<AssignmentDTO>> assignOrderToTechnician(@RequestBody AssignmentDTO assignmentDTO) {
        AssignmentDTO assigned = assignmentService.assignOrderToTechnician(assignmentDTO);
        return ResponseEntity.ok(assignmentAssembler.toModel(assigned));
    }

    @PostMapping("/auto")
    public ResponseEntity<EntityModel<AssignmentDTO>> autoAssign(@RequestParam Integer orderId) {
        AssignmentDTO assigned = assignmentService.assignAutomatically(orderId);
        return ResponseEntity.ok(assignmentAssembler.toModel(assigned));
    }

    @GetMapping("/report")
    public ResponseEntity<CollectionModel<EntityModel<TechnicianAssignmentReportDTO>>> getReport() {
        List<TechnicianAssignmentReportDTO> reports = assignmentService.getAssignmentReport();

        List<EntityModel<TechnicianAssignmentReportDTO>> reportModels = reports.stream()
                .map(reportAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(reportModels,
                linkTo(methodOn(AssignmentController.class).getReport()).withSelfRel()));
    }


}
