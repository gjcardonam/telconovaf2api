package com.telconova.telconovaf2.application.service.notifications;

import com.telconova.telconovaf2.domain.entities.Assignment;
import com.telconova.telconovaf2.domain.service.NotificationServiceI;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements NotificationServiceI {

    @Override
    public void notifyTechnician(Assignment assignment) {
        String technicianName = assignment.getTechnician() != null ? assignment.getTechnician().getName() : "Técnico desconocido";
        String orderName = assignment.getOrder() != null ? assignment.getOrder().getName() : "Orden desconocida";
        String zoneName = (assignment.getOrder() != null && assignment.getOrder().getZone() != null)
                ? assignment.getOrder().getZone().getName()
                : "Zona no especificada";
        String timestamp = assignment.getTimeStamp() != null ? assignment.getTimeStamp().toString() : "Sin hora";

        String message = String.format(
                "📢 Técnico asignado: %s\nOrden: %s\nZona: %s\nHora: %s",
                technicianName, orderName, zoneName, timestamp
        );

        System.out.println(message);
    }

}
