package ucentral.edu.co.XtremePark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucentral.edu.co.XtremePark.model.Payment;
import ucentral.edu.co.XtremePark.service.PaymentService;

@RestController
@RequestMapping("/api/pagos")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/procesar")
    public ResponseEntity<?> procesar(@RequestParam Long reservaId,
                                      @RequestParam Double amount,
                                      @RequestParam String currency,
                                      @RequestParam(required = false) String providerRef) {
        try {
            Payment p = paymentService.procesarPago(reservaId, amount, currency, providerRef);
            return ResponseEntity.ok(p);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
