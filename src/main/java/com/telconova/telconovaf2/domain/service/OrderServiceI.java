package com.telconova.telconovaf2.domain.service;

import com.telconova.telconovaf2.infrastructure.dto.FiltersDTO;
import com.telconova.telconovaf2.infrastructure.dto.OrderDTO;

import java.util.List;

public interface OrderServiceI {

    OrderDTO getOrderById(Integer orderId);

    List<OrderDTO> getAllOrders();

    List<OrderDTO> searchOrders(FiltersDTO filters);
}
