package ucentral.edu.co.XtremePark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucentral.edu.co.XtremePark.model.Notification;
import ucentral.edu.co.XtremePark.model.User;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}
