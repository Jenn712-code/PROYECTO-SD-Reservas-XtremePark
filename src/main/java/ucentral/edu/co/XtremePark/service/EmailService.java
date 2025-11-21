package ucentral.edu.co.XtremePark.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ucentral.edu.co.XtremePark.model.Notification;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotificationEmail(String to, Notification notification) throws MessagingException {
        String subject = "Confirmación de reserva en Xtreme Park";

        // Contenido HTML con tabla estilizada
        String message = "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #2E7D32;'>Hola,</h2>" +
                "<p>Tu reserva ha sido registrada exitosamente.</p>" +
                "<table border='1' cellpadding='8' cellspacing='0' style='border-collapse: collapse; width: 100%;'>" +
                "<tr style='background-color: #2E7D32; color: white;'>" +
                "<th>Detalle</th><th>Información</th>" +
                "</tr>" +
                "<tr style='background-color: #C8E6C9;'><td>Fecha</td><td>" + notification.getFecha() + "</td></tr>" +
                "<tr><td>Hora</td><td>" + notification.getHora() + "</td></tr>" +
                "<tr style='background-color: #C8E6C9;'><td>Piloto</td><td>" + notification.getPiloto() + "</td></tr>" +
                "<tr><td>Cargos adicionales</td><td>" + notification.getCargosAdicionales() + "</td></tr>" +
                "<tr style='background-color: #C8E6C9;'><td>Total a pagar</td><td>USD" + notification.getTotalPagar() + "</td></tr>" +
                "<tr><td>Actividad</td><td>" + notification.getActividad() + "</td></tr>" +
                "<tr style='background-color: #C8E6C9;'><td>Método de pago</td><td>" + notification.getMetodoPago() + "</td></tr>" +
                "</table>" +
                "<p>Gracias por tu reserva.</p>" +
                "</body>" +
                "</html>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom("xtremepark77@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(message, true);  // true = HTML

        mailSender.send(mimeMessage);
    }
}
