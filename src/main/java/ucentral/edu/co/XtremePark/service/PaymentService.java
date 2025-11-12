package ucentral.edu.co.XtremePark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucentral.edu.co.XtremePark.model.Payment;
import ucentral.edu.co.XtremePark.model.Reservation;
import ucentral.edu.co.XtremePark.repository.PaymentRepository;
import ucentral.edu.co.XtremePark.repository.ReservationRepository;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;

    public PaymentService(PaymentRepository paymentRepository, ReservationRepository reservationRepository, NotificationService notificationService) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Payment procesarPago(Long reservaId, Double amount, String currency, String providerReference) {
        Reservation r = reservationRepository.findById(reservaId).orElse(null);
        if (r == null) throw new IllegalArgumentException("Reserva no encontrada");

        Payment p = new Payment();
        p.setReserva(r);
        p.setAmount(amount);
        p.setCurrency(currency);
        p.setProviderReference(providerReference);
        p.setCreatedAt(LocalDateTime.now());
        p.setStatus("completed");

        Payment saved = paymentRepository.save(p);

        // actualizar estado de la reserva
        r.setEstado("confirmada");
        reservationRepository.save(r);

        // notificar al usuario
        notificationService.crearNotificacion(r.getUsuario(), "Pago recibido para la reserva id=" + r.getId());

        return saved;
    }
}
