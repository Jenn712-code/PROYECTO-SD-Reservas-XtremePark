package ucentral.edu.co.XtremePark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucentral.edu.co.XtremePark.model.Experience;
import ucentral.edu.co.XtremePark.dto.ExperienceDTO;
import ucentral.edu.co.XtremePark.service.ExperienceService;

import java.util.List;

/**
 * Controlador REST para gestionar experiencias.
 */
@RestController
@RequestMapping("/api/experiencias")
public class ExperienceController {
    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping
    public List<ExperienceDTO> listar(@RequestParam(required = false, defaultValue = "es") String lang,
                                   @RequestParam(required = false, defaultValue = "USD") String moneda) {
        // Devuelve experiencias localizadas y con precios convertidos.
        return experienceService.listarLocalizados(lang, moneda);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experience> obtener(@PathVariable Long id) {
        Experience e = experienceService.obtener(id);
        if (e == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(e);
    }

    @PostMapping
    public ResponseEntity<Experience> crear(@RequestBody Experience e) {
        Experience creado = experienceService.crear(e);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Experience cambios) {
        // Solo administradores pueden actualizar (verificación simple de rol)
        var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) return ResponseEntity.status(403).body("Acceso denegado");

        Experience orig = experienceService.obtener(id);
        if (orig == null) return ResponseEntity.notFound().build();
        // aplicar cambios mínimos
        orig.setNombreEs(cambios.getNombreEs());
        orig.setNombreEn(cambios.getNombreEn());
        orig.setNombreFr(cambios.getNombreFr());
        orig.setNombreJa(cambios.getNombreJa());
        orig.setNombrePt(cambios.getNombrePt());
        orig.setDescripcionEs(cambios.getDescripcionEs());
        orig.setDescripcionEn(cambios.getDescripcionEn());
        orig.setDescripcionFr(cambios.getDescripcionFr());
        orig.setDescripcionJa(cambios.getDescripcionJa());
        orig.setDescripcionPt(cambios.getDescripcionPt());
        orig.setPrecio(cambios.getPrecio());
        orig.setMoneda(cambios.getMoneda());
        orig.setDisponible(cambios.getDisponible());
        experienceService.crear(orig);
        return ResponseEntity.ok(orig);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) return ResponseEntity.status(403).body("Acceso denegado");
        Experience e = experienceService.obtener(id);
        if (e == null) return ResponseEntity.notFound().build();
        // eliminar mediante repository a través del servicio
        experienceService.delete(id);
        return ResponseEntity.ok().build();
    }
}
