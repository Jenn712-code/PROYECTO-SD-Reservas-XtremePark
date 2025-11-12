package ucentral.edu.co.XtremePark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucentral.edu.co.XtremePark.model.User;
import ucentral.edu.co.XtremePark.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> listar() { return userService.listarTodos(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        User u = userService.obtener(id);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(u);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody User u) {
        User creado = userService.crear(u);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody User cambios) {
        User updated = userService.actualizar(id, cambios);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        userService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
