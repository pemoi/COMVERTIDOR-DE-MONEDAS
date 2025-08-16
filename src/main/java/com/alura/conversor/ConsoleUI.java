package com.alura.conversor;

import com.alura.conversor.model.ConversionResult;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleUI {
    private final CurrencyConverter converter;
    private final Scanner scanner = new Scanner(System.in);
    private final NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es", "ES"));

    public ConsoleUI(CurrencyConverter converter) {
        this.converter = converter;
    }

    public void start() {
        printWelcome();
        int option;
        do {
            printMenu();
            option = readInt("Elige una opción: ");
            switch (option) {
                case 1 -> convert("USD", "ARS");
                case 2 -> convert("ARS", "USD");
                case 3 -> convert("BRL", "USD");
                case 4 -> convert("USD", "BRL");
                case 5 -> convert("COP", "USD");
                case 6 -> convert("USD", "COP");
                case 7 -> freeConversion();
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida.");
            }
            System.out.println();
        } while (option != 0);
    }

    private void printWelcome() {
        System.out.println("===========================================");
        System.out.println("  Conversor de Monedas - Java + API");
        System.out.println("===========================================\n");
    }

    private void printMenu() {
        System.out.println("Menú de opciones:");
        System.out.println("1) USD -> ARS");
        System.out.println("2) ARS -> USD");
        System.out.println("3) BRL -> USD");
        System.out.println("4) USD -> BRL");
        System.out.println("5) COP -> USD");
        System.out.println("6) USD -> COP");
        System.out.println("7) Conversión libre (elige monedas)");
        System.out.println("0) Salir");
    }

    private void convert(String from, String to) {
        double amount = readDouble("Ingresa el monto a convertir (" + from + "): ");
        try {
            ConversionResult res = converter.convert(from, to, amount);
            var amountStr = nf.format(amount);
            var resultStr = nf.format(res.result());
            System.out.printf("El valor de %s %s corresponde a %s %s (tasa: %.6f)%n",
                    amountStr, from, resultStr, to, res.rate());
        } catch (Exception ex) {
            System.out.println("Error al convertir: " + ex.getMessage());
        }
    }

    private void freeConversion() {
        String from = readString("Moneda origen (ej: USD): ").toUpperCase();
        String to = readString("Moneda destino (ej: ARS): ").toUpperCase();
        convert(from, to);
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingresa un número válido: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    private double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Ingresa un número válido: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return value;
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
