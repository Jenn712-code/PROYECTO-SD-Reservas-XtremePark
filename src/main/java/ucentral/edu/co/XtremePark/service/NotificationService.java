package ucentral.edu.co.XtremePark.service;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucentral.edu.co.XtremePark.model.Notification;
import ucentral.edu.co.XtremePark.repository.NotificationRepository;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }

    public class NotificationAlreadyExistsException extends RuntimeException {
        public NotificationAlreadyExistsException(String message) {
            super(message);
        }
    }

    public Notification saveNotification(Notification notification) {
        // Validación de fecha y hora
        notificationRepository.findByFechaAndHora(notification.getFecha(), notification.getHora())
                .ifPresent(n -> {
                    throw new NotificationAlreadyExistsException(
                            "Ya existe una reserva extrema programada para la fecha "
                                    + notification.getFecha() + " y hora " + notification.getHora()
                    );
                });

        // Guardar en base de datos
        Notification savedNotification = notificationRepository.save(notification);

        // Enviar correo de confirmación
        try {
            emailService.sendNotificationEmail(savedNotification.getEmail(), savedNotification);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Aquí puedes mostrar un mensaje de error o manejar la situación
        }

        return savedNotification;
    }

    /*public Notification crearNotificacion(User usuario, String mensaje) {
        Notification n = new Notification();
        n.setUser(usuario);
        n.setMessage(mensaje);
        n.setLeida(false);
        return notificationRepository.save(n);
    }

    public List<Notification> listarPorUsuario(User usuario) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(usuario);
    }*/
}