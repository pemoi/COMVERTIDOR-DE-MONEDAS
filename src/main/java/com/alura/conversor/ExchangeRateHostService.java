package com.alura.conversor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Implementación usando exchangerate.host (sin API key).
 * Endpoint: https://api.exchangerate.host/convert?from=USD&to=ARS
 */
public class ExchangeRateHostService implements RateService {
    private final HttpClient http = HttpClient.newHttpClient();

    @Override
    public double fetchRate(String from, String to) {
        String url = "https://api.exchangerate.host/convert?from=" +
                URLEncoder.encode(from, StandardCharsets.UTF_8) +
                "&to=" + URLEncoder.encode(to, StandardCharsets.UTF_8);
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        try {
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200) {
                throw new RuntimeException("HTTP " + resp.statusCode() + " al consultar exchangerate.host");
            }
            JsonObject json = JsonParser.parseString(resp.body()).getAsJsonObject();
            if (json.has("info") && json.getAsJsonObject("info").has("rate")) {
                return json.getAsJsonObject("info").get("rate").getAsDouble();
            }
            if (json.has("success") && !json.get("success").getAsBoolean()) {
                throw new RuntimeException("API error (exchangerate.host)");
            }
            throw new RuntimeException("Respuesta inesperada de exchangerate.host");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error de red: " + e.getMessage(), e);
        }
    }
}
