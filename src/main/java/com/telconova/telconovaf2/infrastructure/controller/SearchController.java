package com.telconova.telconovaf2.infrastructure.controller;

import com.telconova.telconovaf2.application.service.search.OrderService;
import com.telconova.telconovaf2.infrastructure.assembler.OrderModelAssembler;
import com.telconova.telconovaf2.infrastructure.dto.FiltersDTO;
import com.telconova.telconovaf2.infrastructure.dto.OrderDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/orders")
public class SearchController {

    private final OrderService orderService;
    private final OrderModelAssembler orderAssembler;

    public SearchController(OrderService orderService, OrderModelAssembler orderAssembler) {
        this.orderService = orderService;
        this.orderAssembler = orderAssembler;
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<EntityModel<OrderDTO>> getOrderById(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId)
                .map(orderAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        List<EntityModel<OrderDTO>> orderModels = orders.stream()
                .map(orderAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(orderModels,
                linkTo(methodOn(SearchController.class).getAllOrders()).withSelfRel()));
    }

    @GetMapping("/searchByFilters")
    public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getOrdersByFilters(
            @RequestParam(required = false) String zoneName,
            @RequestParam(required = false) String specialtyName,
            @RequestParam(required = false) String supervisorName,
            @RequestParam(required = false) String technicianName) {
        FiltersDTO filters = new FiltersDTO(zoneName, specialtyName, supervisorName, technicianName);
        List<OrderDTO> filteredOrders = orderService.searchOrders(filters);

        List<EntityModel<OrderDTO>> orderModels = filteredOrders.stream()
                .map(orderAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(orderModels,
                linkTo(methodOn(SearchController.class).getOrdersByFilters(zoneName, specialtyName, supervisorName, technicianName)).withSelfRel()));
    }


}
