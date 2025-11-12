package ucentral.edu.co.XtremePark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucentral.edu.co.XtremePark.model.Payment;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByReservaId(Long reservaId);
}
