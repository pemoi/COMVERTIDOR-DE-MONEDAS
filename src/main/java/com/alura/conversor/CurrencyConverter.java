package com.alura.conversor;

import com.alura.conversor.model.ConversionResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrencyConverter {
    private final RateService rateService;
    private final Path historyPath = Path.of("conversions.csv");

    public CurrencyConverter(RateService rateService) {
        this.rateService = rateService;
    }

    public ConversionResult convert(String from, String to, double amount) {
        if (amount < 0) throw new IllegalArgumentException("El monto no puede ser negativo.");
        double rate = rateService.fetchRate(from, to);
        double result = amount * rate;
        var cr = new ConversionResult(from, to, amount, rate, result);
        appendHistory(cr);
        return cr;
    }

    private void appendHistory(ConversionResult c) {
        try {
            if (Files.notExists(historyPath)) {
                try (var w = Files.newBufferedWriter(historyPath)) {
                    w.write("timestamp,from,to,amount,rate,result\n");
                }
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(historyPath.toFile(), true))) {
                String ts = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                bw.write(String.format("%s,%s,%s,%.6f,%.6f,%.6f%n",
                        ts, c.from(), c.to(), c.amount(), c.rate(), c.result()));
            }
        } catch (IOException e) {
            // No interrumpir la experiencia del usuario si no puede escribir el historial
            System.err.println("No se pudo escribir el historial: " + e.getMessage());
        }
    }
}
