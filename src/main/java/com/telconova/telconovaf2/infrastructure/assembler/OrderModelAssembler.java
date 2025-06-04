package com.telconova.telconovaf2.infrastructure.assembler;

import com.telconova.telconovaf2.infrastructure.controller.AssignmentController;
import com.telconova.telconovaf2.infrastructure.controller.SearchController;
import com.telconova.telconovaf2.infrastructure.dto.OrderDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<OrderDTO, EntityModel<OrderDTO>> {

    @Override
    public EntityModel<OrderDTO> toModel(OrderDTO order) {
        return EntityModel.of(order,
                linkTo(methodOn(SearchController.class).getOrderById(order.getId())).withSelfRel(),
                linkTo(methodOn(AssignmentController.class).autoAssign(order.getId())).withRel("auto-assign")
        );
    }
}
