package com.example.racerank.controller;

import com.example.racerank.dto.PilotoDto;
import com.example.racerank.service.PilotoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroPilotoController {
    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoEquipe;

    private PilotoService pilotoService = new PilotoService();
    private PilotoDto pilotoEdicao;

    @FXML
    public void salvar(ActionEvent event) {
        String nome = campoNome.getText();
        String equipe = campoEquipe.getText();

        //impede de salvar campos vazios
        if (nome == null || nome.trim().isEmpty() || equipe == null || equipe.trim().isEmpty()) {
            mostrarAlerta("Erro de Validação", "Todos os campos devem ser preenchidos na criação!");
            return; // Para o método aqui e não fecha a janela
        }

        try {
            String metodo = (pilotoEdicao == null) ? "POST" : "PUT";

            PilotoDto dto = new PilotoDto();
            if (pilotoEdicao != null) dto.setId(pilotoEdicao.getId());

            dto.setNome(nome);
            dto.setEquipe(equipe);

            pilotoService.salvarPiloto(dto, metodo);

            //fecha a janela apenas se salvar com sucesso
            fecharJanela(event);
        }
        catch (Exception exception) {
            mostrarAlerta("Erro", "Erro ao salvar no servidor: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    @FXML
    public void cancelar(ActionEvent event) {
        fecharJanela(event);
    }

    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    //metodo para receber o piloto caso seja uma edição
    public void setPiloto(PilotoDto piloto) {
        this.pilotoEdicao = piloto;
        if (piloto != null) {
            campoNome.setText(piloto.getNome());
            campoEquipe.setText(piloto.getEquipe());
        }
    }

}