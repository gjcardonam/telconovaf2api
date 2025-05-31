package com.telconova.telconovaf2.application.service.notifications;

import com.telconova.telconovaf2.domain.entities.Assignment;
import com.telconova.telconovaf2.domain.service.NotificationServiceI;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements NotificationServiceI {

    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notifyTechnician(Assignment assignment) {
        String technicianName = assignment.getTechnician() != null ? assignment.getTechnician().getName() : "Técnico desconocido";
        String technicianEmail = assignment.getTechnician() != null ? assignment.getTechnician().getEmail() : null;
        String orderName = assignment.getOrder() != null ? assignment.getOrder().getName() : "Orden desconocida";
        String zoneName = (assignment.getOrder() != null && assignment.getOrder().getZone() != null)
                ? assignment.getOrder().getZone().getName()
                : "Zona no especificada";
        String timestamp = assignment.getTimeStamp() != null ? assignment.getTimeStamp().toString() : "Sin hora";

        String message = String.format(
                "📢 Hola %s,\n\nSe te ha asignado la orden: %s\nZona: %s\nHora: %s\n\nGracias.",
                technicianName, orderName, zoneName, timestamp
        );

        if (technicianEmail != null) {
            emailService.sendEmail(
                    technicianEmail,
                    "Nueva asignación de orden",
                    message
            );
        } else {
            System.out.println("⚠️ No se pudo enviar el correo: técnico sin correo asignado.");
        }

        // También imprime en consola para debugging
        System.out.println("[Notificación enviada] " + message);
    }
}
