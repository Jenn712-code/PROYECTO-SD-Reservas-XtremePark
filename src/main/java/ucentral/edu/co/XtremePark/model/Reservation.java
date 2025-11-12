package ucentral.edu.co.XtremePark.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Entidad Reserva: relaciona usuario con experiencia y periodo.
 */
@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "experiencia_id")
    private Experience experiencia;

    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    private String estado; // pendiente, confirmada, cancelada

    @ManyToOne
    @JoinColumn(name = "piloto_id")
    private User pilotoAsignado; // piloto elegido para la reserva (opcional)
}
