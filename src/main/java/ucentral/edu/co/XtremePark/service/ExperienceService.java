package ucentral.edu.co.XtremePark.service;

import org.springframework.stereotype.Service;
import ucentral.edu.co.XtremePark.dto.ExperienceDTO;
import ucentral.edu.co.XtremePark.model.Experience;
import ucentral.edu.co.XtremePark.repository.ExperienceRepository;
import ucentral.edu.co.XtremePark.service.CurrencyService;
import ucentral.edu.co.XtremePark.service.TranslationService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para operaciones relacionadas con Experiencias.
 * Proporciona métodos para obtener experiencias localizadas y con precio convertido.
 */
@Service
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final CurrencyService currencyService;
    private final TranslationService translationService;

    public ExperienceService(ExperienceRepository experienceRepository, CurrencyService currencyService, TranslationService translationService) {
        this.experienceRepository = experienceRepository;
        this.currencyService = currencyService;
        this.translationService = translationService;
    }

    public List<Experience> listarTodas() {
        return experienceRepository.findAll();
    }

    public Experience crear(Experience e) {
        return experienceRepository.save(e);
    }

    public Experience obtener(Long id) {
        return experienceRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        experienceRepository.deleteById(id);
    }

    /**
     * Devuelve DTOs localizados según el idioma y moneda solicitados.
     */
    public List<ExperienceDTO> listarLocalizados(String lang, String targetMoneda) {
        return experienceRepository.findAll().stream().map(e -> toDTO(e, lang, targetMoneda)).collect(Collectors.toList());
    }

    public ExperienceDTO toDTO(Experience e, String lang, String targetMoneda) {
        ExperienceDTO dto = new ExperienceDTO();
        dto.id = e.getId();
        dto.disponible = e.getDisponible();
        // seleccionar nombre/descripcion según lang
        switch (lang) {
            case "en":
                if (e.getNombreEn() == null || e.getNombreEn().isBlank()) {
                    String tn = translationService.translate(e.getNombreEs(), "es", "en");
                    e.setNombreEn(tn);
                    // intentar persistir la traducción para evitar recálculos posteriores
                    experienceRepository.save(e);
                }
                if (e.getDescripcionEn() == null || e.getDescripcionEn().isBlank()) {
                    String td = translationService.translate(e.getDescripcionEs(), "es", "en");
                    e.setDescripcionEn(td);
                    experienceRepository.save(e);
                }
                dto.nombre = e.getNombreEn(); dto.descripcion = e.getDescripcionEn();
                break;
            case "fr":
                if (e.getNombreFr() == null || e.getNombreFr().isBlank()) {
                    String tn = translationService.translate(e.getNombreEs(), "es", "fr");
                    e.setNombreFr(tn); experienceRepository.save(e);
                }
                if (e.getDescripcionFr() == null || e.getDescripcionFr().isBlank()) {
                    String td = translationService.translate(e.getDescripcionEs(), "es", "fr");
                    e.setDescripcionFr(td); experienceRepository.save(e);
                }
                dto.nombre = e.getNombreFr(); dto.descripcion = e.getDescripcionFr();
                break;
            case "ja":
                if (e.getNombreJa() == null || e.getNombreJa().isBlank()) {
                    String tn = translationService.translate(e.getNombreEs(), "es", "ja");
                    e.setNombreJa(tn); experienceRepository.save(e);
                }
                if (e.getDescripcionJa() == null || e.getDescripcionJa().isBlank()) {
                    String td = translationService.translate(e.getDescripcionEs(), "es", "ja");
                    e.setDescripcionJa(td); experienceRepository.save(e);
                }
                dto.nombre = e.getNombreJa(); dto.descripcion = e.getDescripcionJa();
                break;
            case "pt":
                if (e.getNombrePt() == null || e.getNombrePt().isBlank()) {
                    String tn = translationService.translate(e.getNombreEs(), "es", "pt");
                    e.setNombrePt(tn); experienceRepository.save(e);
                }
                if (e.getDescripcionPt() == null || e.getDescripcionPt().isBlank()) {
                    String td = translationService.translate(e.getDescripcionEs(), "es", "pt");
                    e.setDescripcionPt(td); experienceRepository.save(e);
                }
                dto.nombre = e.getNombrePt(); dto.descripcion = e.getDescripcionPt();
                break;
            default:
                dto.nombre = e.getNombreEs(); dto.descripcion = e.getDescripcionEs();
        }

        // conversión de moneda
        double factor = 1.0;
        if (e.getMoneda() != null && !e.getMoneda().equalsIgnoreCase(targetMoneda)) {
            factor = currencyService.obtenerFactor(e.getMoneda(), targetMoneda);
        }
        dto.moneda = targetMoneda;
        dto.precio = e.getPrecio() * factor;
        return dto;
    }
}
