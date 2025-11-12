package ucentral.edu.co.XtremePark.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servicio que obtiene tasas de cambio reales usando exchangerate.host API (gratuito, no requiere clave).
 * Devuelve factor para convertir desde una moneda origen a otra.
 */
@Service
public class CurrencyService {
    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Obtiene el factor de conversión de from->to usando exchangerate.host
     */
    public double obtenerFactor(String from, String to) {
        try {
            String url = String.format("https://api.exchangerate.host/convert?from=%s&to=%s", from, to);
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            JsonNode root = mapper.readTree(resp.body());
            if (root.has("info") && root.get("info").has("rate")) {
                return root.get("info").get("rate").asDouble();
            }
        } catch (Exception ex) {
            // En caso de error, se retorna 1.0 para evitar fallos; idealmente loggear
            ex.printStackTrace();
        }
        return 1.0;
    }
}
