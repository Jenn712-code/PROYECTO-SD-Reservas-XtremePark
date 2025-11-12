package ucentral.edu.co.XtremePark.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidad Notificación para notificar a pilotos/usuarios.
 */
@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user; // destinatario

    @Column(length = 1000)
    private String message;

    private boolean leida = false;

    private LocalDateTime createdAt = LocalDateTime.now();
}
