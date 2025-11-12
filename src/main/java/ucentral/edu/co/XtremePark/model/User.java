package ucentral.edu.co.XtremePark.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad Usuario básica para XtremePark
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    private String telefono;

    @Column(nullable = false)
    private String password; // contraseña encriptada

    @Column(nullable = false)
    private String rol; // cliente, administrador, piloto
}
