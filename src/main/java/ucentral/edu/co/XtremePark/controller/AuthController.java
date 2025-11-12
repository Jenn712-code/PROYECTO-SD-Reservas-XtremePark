package ucentral.edu.co.XtremePark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ucentral.edu.co.XtremePark.model.User;
import ucentral.edu.co.XtremePark.repository.UserRepository;
import ucentral.edu.co.XtremePark.security.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        User u = userRepository.findAll().stream().filter(x -> x.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
        if (u == null) return ResponseEntity.status(401).body("Usuario no encontrado");
        if (!passwordEncoder.matches(password, u.getPassword())) return ResponseEntity.status(401).body("Credenciales inválidas");
        String token = jwtUtil.generateToken(u.getEmail(), u.getRol());
        return ResponseEntity.ok(Map.of("token", token, "role", u.getRol(), "userId", u.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User u) {
        if (userRepository.findAll().stream().anyMatch(x -> x.getEmail().equalsIgnoreCase(u.getEmail()))) {
            return ResponseEntity.badRequest().body("Email ya registrado");
        }
        // codificar contraseña
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        User saved = userRepository.save(u);
        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRol());
        return ResponseEntity.ok(Map.of("token", token, "userId", saved.getId(), "role", saved.getRol()));
    }
}
