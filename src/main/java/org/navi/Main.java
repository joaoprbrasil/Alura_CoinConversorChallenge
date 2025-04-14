package org.navi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/18a3e19e0ab2314bea674f3d/latest/BRL"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        ExchangeResponse data = gson.fromJson(response.body(), ExchangeResponse.class);

        Map<String, Double> taxas = data.conversion_rates;

        while (true) {
            System.out.println("\n=== CONVERSOR DE MOEDAS (Base: BRL) ===");
            System.out.println("1. BRL -> USD (Dólar Americano)");
            System.out.println("2. BRL -> EUR (Euro)");
            System.out.println("3. BRL -> GBP (Libra Esterlina)");
            System.out.println("4. BRL -> JPY (Iene Japonês)");
            System.out.println("5. BRL -> CAD (Dólar Canadense)");
            System.out.println("6. BRL -> ARS (Peso Argentino)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();

            if (opcao == 0) {
                System.out.println("Encerrando o conversor. Até logo!");
                break;
            }

            String[] moedas = {"USD", "EUR", "GBP", "JPY", "CAD", "ARS"};
            if (opcao < 1 || opcao > moedas.length) {
                System.out.println("Opção inválida. Tente novamente.");
                continue;
            }

            String moedaDestino = moedas[opcao - 1];
            double taxa = taxas.get(moedaDestino);

            System.out.print("Digite o valor em BRL: ");
            double valorBRL = scanner.nextDouble();
            double valorConvertido = valorBRL * taxa;

            System.out.printf("R$%.2f BRL = %.2f %s\n", valorBRL, valorConvertido, moedaDestino);
        }
    }
}
