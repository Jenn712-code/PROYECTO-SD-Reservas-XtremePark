package ucentral.edu.co.XtremePark.service;

import org.springframework.stereotype.Service;
import ucentral.edu.co.XtremePark.model.Notification;
import ucentral.edu.co.XtremePark.model.User;
import ucentral.edu.co.XtremePark.repository.NotificationRepository;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification crearNotificacion(User usuario, String mensaje) {
        Notification n = new Notification();
        n.setUser(usuario);
        n.setMessage(mensaje);
        n.setLeida(false);
        return notificationRepository.save(n);
    }

    public List<Notification> listarPorUsuario(User usuario) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(usuario);
    }
}
