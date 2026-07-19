package com.example.racerank.service;

import com.example.racerank.dto.PilotoDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PilotoService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310
            .JavaTimeModule());
    private final String API_URL = "http://localhost:8080/piloto";

    public List<PilotoDto> buscarTodosPilotos() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        //convertendo o JSON da API para uma lista de PilotoDto
        return objectMapper.readValue(response.body(), new TypeReference<List<PilotoDto>>(){});
    }
    //delete
    public void deletarPiloto(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id)) // Ex: http://localhost:8080/piloto/3
                .DELETE()
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    //uptade and create
    public void salvarPiloto(PilotoDto piloto, String metodo) throws Exception {
        String json = objectMapper.writeValueAsString(piloto);

        // Se for PUT, a URL precisa incluir o ID
        String url = API_URL + (metodo.equals("PUT") ? "/" + piloto.getId() : "");

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json");

        if (metodo.equals("POST")) {
            builder.POST(HttpRequest.BodyPublishers.ofString(json));
        } else {
            builder.PUT(HttpRequest.BodyPublishers.ofString(json));
        }

        HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        // Log para ver o que a API respondeu
        System.out.println("Status da resposta da API: " + response.statusCode());
        System.out.println("Resposta da API: " + response.body());
    }
}
