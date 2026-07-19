package com.example.racerank.controller;

import com.example.racerank.dto.PistaDto;
import com.example.racerank.dto.RegistroDaVoltaDto;
import com.example.racerank.service.PistaService;
import com.example.racerank.service.RankingService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RankingController implements Initializable {

    @FXML
    private TableView<RegistroDaVoltaDto> tabelaRanking;

    @FXML
    private TableColumn<RegistroDaVoltaDto, String> colPiloto;

    @FXML
    private TableColumn<RegistroDaVoltaDto, String> colPista;

    @FXML
    private TableColumn<RegistroDaVoltaDto, String> colTempo;

    @FXML
    private ComboBox<PistaDto> comboPistas;

    private final RankingService rankingService = new RankingService();
    private final PistaService pistaService = new PistaService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        carregarRankingGeral();

        try {
            List<PistaDto> pistas = pistaService.buscarTodasPistas();
            comboPistas.setItems(FXCollections.observableArrayList(pistas));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void configurarColunas() {
        // Coluna Piloto
        colPiloto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPiloto().getNome()));

        // Coluna Pista
        colPista.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPista().getNome()));

        // Coluna Tempo formatada
        colTempo.setCellValueFactory(data -> {
            long ms = data.getValue().getTempoMilissegundos();
            long min = (ms / 1000) / 60;
            long sec = (ms / 1000) % 60;
            long millis = ms % 1000;
            String tempoFormatado = String.format("%02d:%02d.%03d", min, sec, millis);
            return new SimpleStringProperty(tempoFormatado);
        });
    }

    @FXML
    public void carregarRankingGeral() {
        try {
            List<RegistroDaVoltaDto> lista = rankingService.buscarRankingGeral();
            tabelaRanking.setItems(FXCollections.observableArrayList(lista));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onPistaSelecionada() {
        PistaDto pistaSelecionada = comboPistas.getValue();
        if (pistaSelecionada != null) {
            try {
                List<RegistroDaVoltaDto> lista = rankingService.buscarRankingPorPista(pistaSelecionada.getId());
                tabelaRanking.setItems(FXCollections.observableArrayList(lista));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}