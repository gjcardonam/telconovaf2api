package com.telconova.telconovaf2.infrastructure.assembler;

import com.telconova.telconovaf2.infrastructure.controller.AssignmentController;
import com.telconova.telconovaf2.infrastructure.controller.SearchController;
import com.telconova.telconovaf2.infrastructure.dto.AssignmentDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AssignmentModelAssembler implements RepresentationModelAssembler<AssignmentDTO, EntityModel<AssignmentDTO>> {

    @Override
    public EntityModel<AssignmentDTO> toModel(AssignmentDTO assignment) {
        return EntityModel.of(assignment,
                linkTo(methodOn(AssignmentController.class).assignOrderToTechnician(null)).withRel("assign"),
                linkTo(methodOn(AssignmentController.class).autoAssign(assignment.getOrderId())).withRel("auto-assign"),
                linkTo(methodOn(SearchController.class).getOrderById(assignment.getOrderId())).withRel("order")
        );
    }
}

