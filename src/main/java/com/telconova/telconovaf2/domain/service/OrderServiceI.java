package com.telconova.telconovaf2.domain.service;

import com.telconova.telconovaf2.infrastructure.dto.FiltersDTO;
import com.telconova.telconovaf2.infrastructure.dto.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderServiceI {

    Optional<OrderDTO> getOrderById(Integer orderId);

    List<OrderDTO> getAllOrders();

    List<OrderDTO> searchOrders(FiltersDTO filters);
}
