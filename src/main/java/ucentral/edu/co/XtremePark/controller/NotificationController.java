package ucentral.edu.co.XtremePark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ucentral.edu.co.XtremePark.model.Notification;
import ucentral.edu.co.XtremePark.service.NotificationService;

@RestController
@RequestMapping("/notificaciones")
public class NotificationController {
    private final NotificationService notificationService;
    //private final UserRepository userRepository;
    private final ucentral.edu.co.XtremePark.repository.NotificationRepository notificationRepository;

    public NotificationController(NotificationService notificationService, /*UserRepository userRepository,*/ ucentral.edu.co.XtremePark.repository.NotificationRepository notificationRepository) {
        this.notificationService = notificationService;
        //this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    /*@GetMapping
    public ResponseEntity<?> listarPorUsuario(@RequestParam Long userId) {
        User u = userRepository.findById(userId).orElse(null);
        if (u == null) return ResponseEntity.badRequest().body("Usuario no encontrado");
        List<Notification> lista = notificationService.listarPorUsuario(u);
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestParam Long userId, @RequestParam String mensaje) {
        User u = userRepository.findById(userId).orElse(null);
        if (u == null) return ResponseEntity.badRequest().body("Usuario no encontrado");
        Notification n = notificationService.crearNotificacion(u, mensaje);
        return ResponseEntity.ok(n);
    }

    @PostMapping("/marcar-leida/{id}")
    public ResponseEntity<?> marcarLeida(@PathVariable Long id) {
        var opt = notificationRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Notification n = opt.get();
        n.setLeida(true);
        notificationRepository.save(n);
        return ResponseEntity.ok(n);
    }*/

    // Crear o guardar notificación
    @PostMapping("/crear")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        try {
            Notification savedNotification = notificationService.saveNotification(notification);
            return ResponseEntity.ok(savedNotification);
        } catch (NotificationService.NotificationAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
