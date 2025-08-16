package com.alura.conversor;

import com.alura.conversor.util.Env;

public class App {
    public static void main(String[] args) {
        var apiKey = Env.get("EXCHANGE_RATE_API_KEY");
        RateService rateService = (apiKey == null || apiKey.isBlank())
                ? new ExchangeRateHostService()
                : new ExchangeRateApiService(apiKey);

        var ui = new ConsoleUI(new CurrencyConverter(rateService));
        ui.start();
    }
}
