package com.telconova.telconovaf2.infrastructure.controller;

import com.telconova.telconovaf2.application.service.search.OrderService;
import com.telconova.telconovaf2.domain.controller.OrderControllerI;
import com.telconova.telconovaf2.infrastructure.dto.FiltersDTO;
import com.telconova.telconovaf2.infrastructure.dto.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class SearchController implements OrderControllerI {

    private final OrderService orderService;

    public SearchController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Optional<OrderDTO>> getOrderById(@PathVariable Integer orderId) {
        return ResponseEntity.ok((orderService.getOrderById(orderId)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/searchByFilters")
    public ResponseEntity<List<OrderDTO>> getOrdersByFilters(@RequestParam(required = false) String zoneName,
                                   @RequestParam(required = false) String specialtyName,
                                   @RequestParam(required = false) String supervisorName,
                                   @RequestParam(required = false) String technicianName) {
        FiltersDTO filters = new FiltersDTO();
        filters.setZoneName(zoneName);
        filters.setSpecialtyName(specialtyName);
        filters.setSupervisorName(supervisorName);
        filters.setTechnicianName(technicianName);
        return ResponseEntity.ok(orderService.searchOrders(filters));
    }

}
