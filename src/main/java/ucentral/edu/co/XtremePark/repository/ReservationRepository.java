package ucentral.edu.co.XtremePark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucentral.edu.co.XtremePark.model.Reservation;
import java.util.List;

// Repositorio para la entidad Reservation
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUsuarioId(Long usuarioId);
}
