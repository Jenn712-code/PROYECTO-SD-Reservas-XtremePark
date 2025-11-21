package ucentral.edu.co.XtremePark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucentral.edu.co.XtremePark.model.Notification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    //List<Notification> findByUserOrderByCreatedAtDesc(User user);

    Optional<Notification> findByFechaAndHora(LocalDate fecha, LocalTime hora);
}
