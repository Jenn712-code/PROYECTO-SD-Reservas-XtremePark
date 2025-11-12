package ucentral.edu.co.XtremePark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucentral.edu.co.XtremePark.model.Experience;

// Repositorio para la entidad Experience
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
