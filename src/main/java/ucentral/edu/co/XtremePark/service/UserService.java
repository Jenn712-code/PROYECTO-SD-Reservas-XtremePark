package ucentral.edu.co.XtremePark.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucentral.edu.co.XtremePark.model.User;
import ucentral.edu.co.XtremePark.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listarTodos() { return userRepository.findAll(); }

    public User obtener(Long id) { return userRepository.findById(id).orElse(null); }

    @Transactional
    public User crear(User u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }

    @Transactional
    public User actualizar(Long id, User cambios) {
        User orig = userRepository.findById(id).orElse(null);
        if (orig == null) return null;
        orig.setNombre(cambios.getNombre());
        orig.setEmail(cambios.getEmail());
        orig.setTelefono(cambios.getTelefono());
        if (cambios.getPassword() != null && !cambios.getPassword().isBlank()) {
            orig.setPassword(passwordEncoder.encode(cambios.getPassword()));
        }
        orig.setRol(cambios.getRol());
        return userRepository.save(orig);
    }

    public void eliminar(Long id) { userRepository.deleteById(id); }
}
