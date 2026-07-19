package com.example.racerank.service;

import com.example.racerank.dto.RegistroDaVoltaDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankingService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final String API_URL = "http://localhost:8080/registro"; // Mesma URL das voltas

    //metodo privado para buscar os dados brutos da API
    private List<RegistroDaVoltaDto> buscarTodasVoltas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Cache-Control", "no-cache")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<List<RegistroDaVoltaDto>>() {});
    }

    // Ranking Geral: Ordenado do menor tempo para o maior
    public List<RegistroDaVoltaDto> buscarRankingGeral() throws Exception {
        return buscarTodasVoltas().stream()
                .sorted(Comparator.comparingLong(RegistroDaVoltaDto::getTempoMilissegundos))
                .collect(Collectors.toList());
    }

    // Ranking por Pista: Filtra pelo ID da pista e depois ordena
    public List<RegistroDaVoltaDto> buscarRankingPorPista(Long pistaId) throws Exception {
        List<RegistroDaVoltaDto> todas = buscarTodasVoltas();

        return todas.stream()
                // Verifica se a volta tem uma pista e se o ID dela bate com o selecionado
                .filter(v -> v.getPista() != null && v.getPista().getId().equals(pistaId))
                .sorted(Comparator.comparingLong(RegistroDaVoltaDto::getTempoMilissegundos))
                .collect(Collectors.toList());
    }
}
