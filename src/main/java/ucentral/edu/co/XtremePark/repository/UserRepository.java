package ucentral.edu.co.XtremePark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucentral.edu.co.XtremePark.model.User;

// Repositorio para la entidad User
public interface UserRepository extends JpaRepository<User, Long> {
}
