package com.alura.conversor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Implementación usando ExchangeRate-API (v6).
 * Endpoint de par: https://v6.exchangerate-api.com/v6/{API_KEY}/pair/{from}/{to}
 */
public class ExchangeRateApiService implements RateService {
    private final String apiKey;
    private final HttpClient http = HttpClient.newHttpClient();

    public ExchangeRateApiService(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public double fetchRate(String from, String to) {
        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s", apiKey, from, to);
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        try {
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200) {
                throw new RuntimeException("HTTP " + resp.statusCode() + " al consultar ExchangeRate-API");
            }
            JsonObject json = JsonParser.parseString(resp.body()).getAsJsonObject();
            if (json.has("conversion_rate")) {
                return json.get("conversion_rate").getAsDouble();
            }
            if (json.has("result") && json.get("result").getAsString().equalsIgnoreCase("error")) {
                String type = json.has("error-type") ? json.get("error-type").getAsString() : "desconocido";
                throw new RuntimeException("API error: " + type);
            }
            throw new RuntimeException("Respuesta inesperada de ExchangeRate-API");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error de red: " + e.getMessage(), e);
        }
    }
}
