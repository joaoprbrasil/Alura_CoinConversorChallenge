package org.navi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/18a3e19e0ab2314bea674f3d/latest/BRL"))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        ExchangeResponse data = gson.fromJson(response.body(), ExchangeResponse.class);

        List<MoedaValor> array = new ArrayList<>();

        for (Map.Entry<String, Double> entry : data.conversion_rates.entrySet()) {
            array.add(new MoedaValor(entry.getKey(), entry.getValue()));
        }

        for (MoedaValor item : array) {
            System.out.println(item.moeda + " => " + item.valor);
        }
    }
}
