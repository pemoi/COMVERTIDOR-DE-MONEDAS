package com.alura.conversor;

public interface RateService {
    /**
     * Devuelve la tasa de cambio (cuánto vale 1 unidad de 'from' en 'to').
     */
    double fetchRate(String from, String to);
}
