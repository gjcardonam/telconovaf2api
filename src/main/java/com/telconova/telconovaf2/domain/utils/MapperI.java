package com.telconova.telconovaf2.domain.utils;

import com.telconova.telconovaf2.domain.entities.Assignment;
import com.telconova.telconovaf2.domain.entities.Order;
import com.telconova.telconovaf2.infrastructure.dto.AssignmentDTO;
import com.telconova.telconovaf2.infrastructure.dto.OrderDTO;

import java.util.List;

public interface MapperI {
    AssignmentDTO assignmemntToDTO(Assignment assignment);
    Assignment dtoToAssignment(AssignmentDTO assignmentDTO);
    OrderDTO orderToDTO(Order order, Assignment assignment);
    Order dtoToOrder(OrderDTO orderDTO);
}
