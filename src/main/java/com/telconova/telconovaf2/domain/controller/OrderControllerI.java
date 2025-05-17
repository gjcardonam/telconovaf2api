package com.telconova.telconovaf2.domain.controller;

import com.telconova.telconovaf2.infrastructure.dto.OrderDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderControllerI {

    ResponseEntity<Optional<OrderDTO>> getOrderById(Integer orderId);

    ResponseEntity<List<OrderDTO>> getAllOrders();

    ResponseEntity<List<OrderDTO>> getOrdersByFilters(String zoneName, String specialtyName, String supervisorName,String technicianName);

}
