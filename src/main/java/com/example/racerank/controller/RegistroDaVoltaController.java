package com.example.racerank.controller;

import com.example.racerank.dto.PilotoDto;
import com.example.racerank.dto.PistaDto;
import com.example.racerank.dto.RegistroDaVoltaDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;

import com.example.racerank.service.*;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.ResourceBundle;

public class RegistroDaVoltaController implements Initializable {

    //campos do formulário
    @FXML
    private ComboBox<PilotoDto> comboPiloto;

    @FXML
    private ComboBox<PistaDto> comboPista;

    @FXML
    private TextField campoTempo;

    @FXML
    private DatePicker campoData;

    //tabela de últimas voltas
    @FXML private TableView<RegistroDaVoltaDto> tabelaVoltas;
    @FXML private TableColumn<RegistroDaVoltaDto, String> colPiloto, colPista, colTempo, colData;

    private PilotoService pilotoService = new PilotoService();
    private PistaService pistaService = new PistaService();
    private RegistroDaVoltaService RegistroDaVoltaService = new RegistroDaVoltaService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabelas();
        carregarCombos();
        carregarUltimasVoltas();
    }

    private void configurarTabelas() {
        colPiloto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getPiloto().getNome()));
        colPista.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getPista().getNome()));
        colTempo.setCellValueFactory(new PropertyValueFactory<>("tempoMilissegundos"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataVolta"));
    }

    private void carregarCombos() {
        try {
            comboPiloto.setItems(FXCollections.observableArrayList(pilotoService.buscarTodosPilotos()));
            comboPista.setItems(FXCollections.observableArrayList(pistaService.buscarTodasPistas()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    private void salvarVolta() {
        System.out.println("Clicou em registrar!");

        PilotoDto pilotoDto = comboPiloto.getValue();
        PistaDto pistaDto = comboPista.getValue();

        // 1. Converter o texto do campo para Long
        Long tempoMilissegundos = null;
        try {
            tempoMilissegundos = Long.parseLong(campoTempo.getText());
        }
        catch (NumberFormatException exception) {
            System.out.println("Erro: O tempo digitado não é um número válido.");
            exibirAlerta("Erro", "O tempo deve ser um número inteiro (milissegundos)!", Alert.AlertType.ERROR);
            return;
        }

        // 2. Validação corrigida
        if (pilotoDto == null || pistaDto == null || tempoMilissegundos <= 0) {
            System.out.println("Validação falhou: Piloto, Pista ou Tempo inválidos.");
            exibirAlerta("Aviso", "Preencha todos os campos corretamente.", Alert.AlertType.WARNING);
            return;
        }

        try {
            RegistroDaVoltaDto novaVolta = new RegistroDaVoltaDto();
            novaVolta.setPilotoId(pilotoDto.getId());
            novaVolta.setPistaId(pistaDto.getId());
            novaVolta.setTempoMilissegundos(tempoMilissegundos);

            System.out.println("JSON que estou enviando para a API: " + new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(novaVolta));
            RegistroDaVoltaService.registrarVolta(novaVolta);

            System.out.println("Salvo com sucesso!");
            campoTempo.clear(); // Limpa o campo após salvar
            carregarUltimasVoltas(); // Atualiza a tabela
        }
        catch (Exception exception) {
            exception.printStackTrace();
            exibirAlerta("Erro", "Falha ao salvar: " + exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void carregarUltimasVoltas() {
        try {
            List<RegistroDaVoltaDto> lista = RegistroDaVoltaService.buscarTodasVoltas();

            // Use FXCollections.observableArrayList para garantir que o JavaFX "veja" a lista
            tabelaVoltas.setItems(FXCollections.observableArrayList(lista));

            // Força a atualização da tabela na tela
            tabelaVoltas.refresh();

            System.out.println("Tabela atualizada com " + lista.size() + " registros.");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void excluirVoltaSelecionada() {
        RegistroDaVoltaDto selecionada = tabelaVoltas.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            exibirAlerta("Aviso", "Selecione uma volta na tabela primeiro.", Alert.AlertType.WARNING);
            return;
        }

        // Janela de confirmação (Sim/Não)
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setContentText("Tem certeza que deseja excluir esta volta?");

        if (confirmacao.showAndWait().get() == ButtonType.OK) {
            try {
                RegistroDaVoltaService.deletarVolta(selecionada.getId());
                exibirAlerta("Sucesso", "Volta excluída!", Alert.AlertType.INFORMATION);
                carregarUltimasVoltas();
            }
            catch (Exception exception) {
                exibirAlerta("Erro", "Não foi possível excluir.", Alert.AlertType.ERROR);
            }
        }
    }
}
