package ucentral.edu.co.XtremePark.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * Servicio simple para traducciones automáticas.
 * Usa una instancia pública de LibreTranslate por defecto.
 * Nota: para producción se recomienda configurar una instancia propia o un proveedor con SLA.
 */
@Service
public class TranslationService {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String endpoint = "https://libretranslate.de/translate"; // puede cambiarse por env var

    public String translate(String text, String sourceLang, String targetLang) {
        if (text == null || text.isBlank() || sourceLang.equalsIgnoreCase(targetLang)) return text;
        try {
            Map<String, Object> body = Map.of(
                    "q", text,
                    "source", sourceLang,
                    "target", targetLang,
                    "format", "text"
            );
            String json = mapper.writeValueAsString(body);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
                Map<?, ?> r = mapper.readValue(resp.body(), Map.class);
                Object t = r.get("translatedText");
                if (t != null) return t.toString();
            }
        } catch (Exception ex) {
            // no fallar la aplicación por una traducción; simplemente devolver el texto original
            System.err.println("Error traduciendo: " + ex.getMessage());
        }
        return text;
    }
}
