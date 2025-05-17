package com.telconova.telconovaf2.application.service.assignment;

import com.telconova.telconovaf2.domain.entities.*;
import com.telconova.telconovaf2.domain.repository.AssignmentsRepositoryI;
import com.telconova.telconovaf2.domain.repository.OrderRepositoryI;
import com.telconova.telconovaf2.domain.repository.TechnicianRepositoryI;
import com.telconova.telconovaf2.domain.service.AssignmentServiceI;
import com.telconova.telconovaf2.domain.service.NotificationServiceI;
import com.telconova.telconovaf2.domain.utils.MapperI;
import com.telconova.telconovaf2.infrastructure.dto.AssignmentDTO;
import com.telconova.telconovaf2.infrastructure.dto.AssignmentSummaryDTO;
import com.telconova.telconovaf2.infrastructure.dto.TechnicianAssignmentReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class AssigmentService implements AssignmentServiceI {

    private final AssignmentsRepositoryI assignmentsRepository;
    private final OrderRepositoryI orderRepository;
    private final TechnicianRepositoryI technicianRepository;
    private final MapperI mapper;
    private final NotificationServiceI notificationService;

    @Autowired
    public AssigmentService(AssignmentsRepositoryI assignmentsRepository,
                            OrderRepositoryI orderRepository,
                            TechnicianRepositoryI technicianRepository,
                            MapperI mapper,
                            NotificationServiceI notificationService) {
        this.assignmentsRepository = assignmentsRepository;
        this.orderRepository = orderRepository;
        this.technicianRepository = technicianRepository;
        this.mapper = mapper;
        this.notificationService = notificationService;
    }

    @Override
    public AssignmentDTO assignOrderToTechnician(AssignmentDTO assignmentDTO) {
        Integer orderId = assignmentDTO.getOrderId();
        Integer technicianId = assignmentDTO.getTechnicianId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        Technician technician = technicianRepository.findById(technicianId)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        // Eliminar asignación anterior (si existe)
        assignmentsRepository.findByOrderId(orderId).ifPresent(assignmentsRepository::delete);

        Assignment assignment = new Assignment();
        assignment.setOrder(order);
        assignment.setTechnician(technician);
        assignment.setTimeStamp(new Date());

        assignmentsRepository.save(assignment);
        notificationService.notifyTechnician(assignment);

        return mapper.assignmemntToDTO(assignment);
    }

    @Override
    public AssignmentDTO assignAutomatically(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden con ID " + orderId + " no encontrada"));

        List<Technician> candidates = technicianRepository.findByZoneAndSpecialty(order.getZone(), order.getSpecialty());
        if (candidates.isEmpty()) {
            throw new IllegalStateException("No hay técnicos disponibles en la zona '"
                    + order.getZone().getName() + "' y especialidad '"
                    + order.getSpecialty().getName() + "'");
        }

        Technician selected = candidates.stream()
                .min(Comparator.comparing(t -> assignmentsRepository.countByTechnicianId(t.getId())))
                .orElseThrow(() -> new IllegalStateException("Error al seleccionar técnico con menor carga"));

        // Eliminar asignación anterior (si existe)
        assignmentsRepository.findByOrderId(orderId).ifPresent(assignmentsRepository::delete);

        Assignment assignment = new Assignment();
        assignment.setOrder(order);
        assignment.setTechnician(selected);
        assignment.setTimeStamp(new Date());

        assignmentsRepository.save(assignment);
        notificationService.notifyTechnician(assignment);

        return mapper.assignmemntToDTO(assignment);
    }

    @Override
    public List<TechnicianAssignmentReportDTO> getAssignmentReport() {
        List<Technician> technicians = technicianRepository.findAll();

        return technicians.stream()
                .map(tech -> {
                    List<Assignment> assignments = assignmentsRepository.findAllByTechnician(tech);
                    List<AssignmentSummaryDTO> summary = assignments.stream()
                            .map(a -> new AssignmentSummaryDTO(
                                    a.getOrder().getId(),
                                    a.getOrder().getName(),
                                    a.getTimeStamp(),
                                    a.getDuration() != null ? a.getDuration().doubleValue() : null
                            ))
                            .toList();

                    return new TechnicianAssignmentReportDTO(
                            tech.getId(),
                            tech.getName(),
                            tech.getSpecialty() != null ? tech.getSpecialty().getName() : null,
                            tech.getZone() != null ? tech.getZone().getName() : null,
                            summary.size(),
                            summary
                    );
                })
                .toList();
    }
}
