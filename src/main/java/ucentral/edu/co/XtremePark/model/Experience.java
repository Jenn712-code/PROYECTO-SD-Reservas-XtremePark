package ucentral.edu.co.XtremePark.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad Experiencia (actividad) que se puede reservar.
 */
@Entity
@Table(name = "experiencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombres en varios idiomas (es, en, fr, ja, pt)
    @Column(nullable = false)
    private String nombreEs;
    private String nombreEn;
    private String nombreFr;
    private String nombreJa;
    private String nombrePt;

    // Descripciones en varios idiomas
    @Column(length = 2000)
    private String descripcionEs;
    @Column(length = 2000)
    private String descripcionEn;
    @Column(length = 2000)
    private String descripcionFr;
    @Column(length = 2000)
    private String descripcionJa;
    @Column(length = 2000)
    private String descripcionPt;

    private Double precio; // precio en la moneda indicada en 'moneda'

    private String moneda = "USD"; // moneda base del precio (USD por defecto)

    private Boolean disponible = true;
}
