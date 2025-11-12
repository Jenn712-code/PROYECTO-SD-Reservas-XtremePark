package ucentral.edu.co.XtremePark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucentral.edu.co.XtremePark.model.Reservation;
import ucentral.edu.co.XtremePark.service.ReservationService;

import java.util.List;

/**
 * Controlador para gestionar reservas.
 */
@RestController
@RequestMapping("/api/reservas")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> crear(@RequestBody Reservation r) {
        Reservation creado = reservationService.crearReserva(r);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Reservation> porUsuario(@PathVariable Long usuarioId) {
        return reservationService.listarPorUsuario(usuarioId);
    }

    @PostMapping("/{reservaId}/asignar-piloto")
    public ResponseEntity<?> asignarPiloto(@PathVariable Long reservaId, @RequestParam Long pilotoId) {
        try {
            Reservation r = reservationService.asignarPiloto(reservaId, pilotoId);
            return ResponseEntity.ok(r);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
