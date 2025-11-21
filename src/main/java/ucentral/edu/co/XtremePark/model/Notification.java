package ucentral.edu.co.XtremePark.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @Column(name= "email", nullable = false)
    private String email;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name = "piloto")
    private String piloto;

    @Column(name = "cargosAdicionales")
    private String cargosAdicionales;

    @Column(name = "totalPagar", nullable = false)
    private String totalPagar;

    @Column(name = "actividad", nullable = false)
    private String actividad;

    @Column(name = "metodoPago", nullable = false)
    private String metodoPago;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getPiloto() {
        return piloto;
    }

    public void setPiloto(String piloto) {
        this.piloto = piloto;
    }

    public String getCargosAdicionales() {
        return cargosAdicionales;
    }

    public void setCargosAdicionales(String cargosAdicionales) {
        this.cargosAdicionales = cargosAdicionales;
    }

    public String getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(String totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}

/*@ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user; // destinatario

    @Column(length = 1000)
    private String message;

    private boolean leida = false;

    private LocalDateTime createdAt = LocalDateTime.now();*/