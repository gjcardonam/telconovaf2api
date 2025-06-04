package com.telconova.telconovaf2.infrastructure.assembler;

import com.telconova.telconovaf2.infrastructure.controller.AssignmentController;
import com.telconova.telconovaf2.infrastructure.controller.SearchController;
import com.telconova.telconovaf2.infrastructure.dto.TechnicianAssignmentReportDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TechnicianAssignmentReportAssembler implements RepresentationModelAssembler<TechnicianAssignmentReportDTO, EntityModel<TechnicianAssignmentReportDTO>> {

    @Override
    public EntityModel<TechnicianAssignmentReportDTO> toModel(TechnicianAssignmentReportDTO report) {
        EntityModel<TechnicianAssignmentReportDTO> model = EntityModel.of(report);

        // Link al reporte general
        model.add(linkTo(methodOn(AssignmentController.class).getReport()).withRel("assignment-report"));

        // Link a cada orden dentro de las asignaciones
        List<Link> orderLinks = report.getAssignments().stream()
                .map(assignment -> linkTo(methodOn(SearchController.class)
                        .getOrderById(assignment.getOrderId()))
                        .withRel("order-" + assignment.getOrderId()))
                .collect(Collectors.toList());

        model.add(orderLinks);

        return model;
    }
}
