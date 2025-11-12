package ucentral.edu.co.XtremePark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucentral.edu.co.XtremePark.model.Reservation;
import ucentral.edu.co.XtremePark.model.User;
import ucentral.edu.co.XtremePark.repository.ReservationRepository;
import ucentral.edu.co.XtremePark.repository.UserRepository;
import ucentral.edu.co.XtremePark.service.NotificationService;

import java.util.List;

/**
 * Servicio para lógica de reservas.
 */
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, NotificationService notificationService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Reservation crearReserva(Reservation r) {
        // Lógica simple: guardar la reserva (en el futuro: comprobación de disponibilidad)
        r.setEstado(r.getEstado() == null ? "pendiente" : r.getEstado());
        return reservationRepository.save(r);
    }

    public List<Reservation> listarPorUsuario(Long usuarioId) {
        return reservationRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Reservation asignarPiloto(Long reservaId, Long pilotoId) {
        Reservation r = reservationRepository.findById(reservaId).orElse(null);
        if (r == null) throw new IllegalArgumentException("Reserva no encontrada");
        User piloto = userRepository.findById(pilotoId).orElse(null);
        if (piloto == null) throw new IllegalArgumentException("Piloto no encontrado");

        r.setPilotoAsignado(piloto);
        r.setEstado("confirmada");
        Reservation saved = reservationRepository.save(r);

        // notificar al piloto
        notificationService.crearNotificacion(piloto, "Has sido asignado a la reserva id=" + r.getId());

        // notificar al usuario cliente
        notificationService.crearNotificacion(r.getUsuario(), "Tu reserva id=" + r.getId() + " ha sido asignada a un piloto.");

        return saved;
    }
}
