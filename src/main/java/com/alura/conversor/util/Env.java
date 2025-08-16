package com.alura.conversor.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilidad para leer variables de entorno y, opcionalmente, de un archivo .env
 */
public class Env {
    private static Map<String, String> envCache = null;

    public static String get(String key) {
        if (envCache == null) {
            envCache = new HashMap<>(System.getenv());
            // Cargar .env si existe
            Path envPath = Path.of(".env");
            if (Files.exists(envPath)) {
                try {
                    for (String line : Files.readAllLines(envPath)) {
                        line = line.trim();
                        if (line.isEmpty() || line.startsWith("#")) continue;
                        int idx = line.indexOf('=');
                        if (idx > 0) {
                            String k = line.substring(0, idx).trim();
                            String v = line.substring(idx + 1).trim();
                            envCache.putIfAbsent(k, v);
                        }
                    }
                } catch (IOException ignored) {}
            }
        }
        return envCache.get(key);
    }
}
