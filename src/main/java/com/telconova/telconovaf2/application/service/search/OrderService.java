package com.telconova.telconovaf2.application.service.search;

import com.telconova.telconovaf2.domain.entities.Assignment;
import com.telconova.telconovaf2.domain.entities.Order;
import com.telconova.telconovaf2.domain.repository.AssignmentsRepositoryI;
import com.telconova.telconovaf2.domain.repository.OrderRepositoryI;
import com.telconova.telconovaf2.domain.service.OrderServiceI;
import com.telconova.telconovaf2.domain.utils.MapperI;
import com.telconova.telconovaf2.infrastructure.dto.FiltersDTO;
import com.telconova.telconovaf2.infrastructure.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService implements OrderServiceI {

    private final OrderRepositoryI orderRepository;
    private final AssignmentsRepositoryI assignmentRepository;
    private final MapperI mapper;

    @Autowired
    public OrderService(OrderRepositoryI orderRepository, AssignmentsRepositoryI assignmentRepository, MapperI mapper) {
        this.orderRepository = orderRepository;
        this.assignmentRepository = assignmentRepository;
        this.mapper = mapper;
    }

    @Override
    public OrderDTO getOrderById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        List<Assignment> assignments = assignmentRepository.findLatestByOrderId(orderId);
        Assignment assignment = assignments.isEmpty() ? null : assignments.get(0);

        return mapper.orderToDTO(order, assignment);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<Assignment> assignments = assignmentRepository.findAll();

        Map<Integer, Assignment> assignmentMap = assignments.stream()
                .collect(Collectors.toMap(
                        a -> a.getOrder().getId(),
                        a -> a,
                        (a1, a2) -> a1.getTimeStamp().after(a2.getTimeStamp()) ? a1 : a2
                ));

        return orders.stream()
                .map(order -> {
                    Assignment matchingAssignment = assignmentMap.get(order.getId());
                    return mapper.orderToDTO(order, matchingAssignment);
                })
                .toList();
    }

    @Override
    public List<OrderDTO> searchOrders(FiltersDTO filters) {
        List<Order> orders = orderRepository.findByFilters(
                filters.getZoneName(),
                filters.getSpecialtyName(),
                filters.getTechnicianName()
        );

        if (orders.isEmpty()) {
            throw new RuntimeException("Orden no encontrada");
        }

        List<Integer> orderIds = orders.stream()
                .map(Order::getId)
                .toList();

        List<Assignment> assignments = assignmentRepository.findByOrderIdIn(orderIds);

        Map<Integer, Assignment> assignmentMap = assignments.stream()
                .collect(Collectors.toMap(
                        a -> a.getOrder().getId(),
                        a -> a,
                        (a1, a2) -> a1
                ));

        return orders.stream()
                .map(order -> {
                    Assignment assignment = assignmentMap.get(order.getId());
                    return mapper.orderToDTO(order, assignment);
                })
                .toList();
    }
}
