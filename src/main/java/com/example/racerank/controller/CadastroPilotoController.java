package com.example.racerank.controller;

import com.example.racerank.dto.PilotoDto;
import com.example.racerank.service.PilotoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroPilotoController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoEquipe;

    private PilotoService pilotoService = new PilotoService();
    private PilotoDto pilotoEdicao; // Variável para saber se estamos editando

    //metodo chamado pelo botão Salvar
    @FXML
    public void salvar(ActionEvent event) {
        try {
            // Se pilotoEdicao for null, é um POST (Novo), senão é PUT (Editar)
            String metodo = (pilotoEdicao == null) ? "POST" : "PUT";

            PilotoDto dto = new PilotoDto();
            if (pilotoEdicao != null) dto.setId(pilotoEdicao.getId()); // Mantém o ID se for edição

            dto.setNome(campoNome.getText());
            dto.setEquipe(campoEquipe.getText());

            pilotoService.salvarPiloto(dto, metodo);

            // Fecha a janela após salvar
            fecharJanela(event);
        } catch (Exception exception) {
            exception.printStackTrace();
            // Aqui você poderia chamar um mostrarAlerta de erro
        }
    }

    //metodo chamado pelo botão Cancelar
    @FXML
    public void cancelar(ActionEvent event) {
        fecharJanela(event);
    }

    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
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