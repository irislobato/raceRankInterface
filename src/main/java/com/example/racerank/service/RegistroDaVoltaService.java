package com.example.racerank.service;

import com.example.racerank.dto.RegistroDaVoltaDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class RegistroDaVoltaService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final String API_URL = "http://localhost:8080/registro";

    //read
    public List<RegistroDaVoltaDto> buscarTodasVoltas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Cache-Control", "no-cache") // Força a buscar dados novos
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Adicione um log para ver o JSON real que está vindo do seu servidor
        System.out.println("JSON recebido: " + response.body());

        return objectMapper.readValue(response.body(), new TypeReference<List<RegistroDaVoltaDto>>() {});
    }

    //save
    public void registrarVolta(RegistroDaVoltaDto voltaDto) throws Exception {
        String json = objectMapper.writeValueAsString(voltaDto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    //delete
    public void deletarVolta(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id))
                .DELETE()
                .build();

        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}