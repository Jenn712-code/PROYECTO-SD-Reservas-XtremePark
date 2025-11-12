package ucentral.edu.co.XtremePark.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidad Payment para registrar pagos de reservas.
 */
@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reserva_id")
    private Reservation reserva;

    private Double amount;

    private String currency; // e.g. USD, COP

    private String status; // pending, completed, failed

    private String providerReference;

    private LocalDateTime createdAt = LocalDateTime.now();
}
