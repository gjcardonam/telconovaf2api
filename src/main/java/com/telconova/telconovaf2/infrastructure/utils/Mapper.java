package com.telconova.telconovaf2.infrastructure.utils;

import com.telconova.telconovaf2.domain.entities.Assignment;
import com.telconova.telconovaf2.domain.entities.Order;
import com.telconova.telconovaf2.domain.entities.Technician;
import com.telconova.telconovaf2.domain.entities.Zone;
import com.telconova.telconovaf2.domain.utils.MapperI;
import com.telconova.telconovaf2.infrastructure.dto.AssignmentDTO;
import com.telconova.telconovaf2.infrastructure.dto.OrderDTO;
import org.springframework.stereotype.Component;

@Component
public class Mapper implements MapperI {

    @Override
    public AssignmentDTO assignmemntToDTO(Assignment assignment) {
        AssignmentDTO dto = new AssignmentDTO();
        dto.setId(assignment.getId());
        dto.setTechnicianId(assignment.getTechnician() != null ? assignment.getTechnician().getId() : null);
        dto.setTechnicianName(assignment.getTechnician() != null ? assignment.getTechnician().getName() : null);
        dto.setOrderId(assignment.getOrder() != null ? assignment.getOrder().getId() : null);
        dto.setOrderName(assignment.getOrder() != null ? assignment.getOrder().getName() : null);
        dto.setTimestamp(assignment.getTimeStamp());
        return dto;
    }

    @Override
    public Assignment dtoToAssignment(AssignmentDTO dto) {
        Assignment assignment = new Assignment();
        assignment.setId(dto.getId());

        Technician technician = new Technician();
        technician.setId(dto.getTechnicianId());
        technician.setName(dto.getTechnicianName());
        assignment.setTechnician(technician);

        Order order = new Order();
        order.setId(dto.getOrderId());
        order.setName(dto.getOrderName());
        assignment.setOrder(order);

        assignment.setTimeStamp(dto.getTimestamp());
        return assignment;
    }

    public OrderDTO orderToDTO(Order order, Assignment assignment) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setName(order.getName());
        dto.setDescription(order.getDescription());
        dto.setEstimatedTime(order.getEstimatedTime());
        dto.setWorkload(order.getWorkload());
        dto.setUrgency(order.getUrgency());
        dto.setRequester(order.getRequester());
        dto.setTimestamp(order.getTimestamp());

        if (order.getZone() != null) {
            dto.setZoneId(order.getZone().getId());
            dto.setZoneName(order.getZone().getName());
        }

        if (assignment != null && assignment.getTechnician() != null) {
            dto.setTechnicianName(assignment.getTechnician().getName());
            if (assignment.getTechnician().getSpecialty() != null) {
                dto.setSpecialtyName(assignment.getTechnician().getSpecialty().getName());
            }
        }

        return dto;
    }



    @Override
    public Order dtoToOrder(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setName(dto.getName());
        order.setDescription(dto.getDescription());
        order.setEstimatedTime(dto.getEstimatedTime());
        order.setWorkload(dto.getWorkload());
        order.setUrgency(dto.getUrgency());
        order.setRequester(dto.getRequester());
        order.setTimestamp(dto.getTimestamp());

        if (dto.getZoneId() != null || dto.getZoneName() != null) {
            Zone zone = new Zone();
            zone.setId(dto.getZoneId());
            zone.setName(dto.getZoneName());
            order.setZone(zone);
        }

        return order;
    }

}
