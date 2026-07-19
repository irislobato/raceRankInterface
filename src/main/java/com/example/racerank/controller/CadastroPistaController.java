package com.example.racerank.controller;

import com.example.racerank.dto.PistaDto;
import com.example.racerank.service.PistaService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class CadastroPistaController {

    @FXML private TextField campoNome;
    @FXML private TextField campoLocal;
    @FXML private TextField campoDistancia;

    private PistaService pistaService = new PistaService();
    private PistaDto pistaEdicao;

    public void setPista(PistaDto pista) {
        this.pistaEdicao = pista;
        if (pista != null) {
            campoNome.setText(pista.getNome());
            campoLocal.setText(pista.getLocalizacao());
            campoDistancia.setText(String.valueOf(pista.getExtensaoMetros()));
        }
    }

    @FXML
    public void salvar(ActionEvent event) {
        //validação de campos vazios
        if (campoNome.getText().trim().isEmpty() ||
                campoLocal.getText().trim().isEmpty() ||
                campoDistancia.getText().trim().isEmpty()) {

            mostrarAlerta("Erro", "Todos os campos devem ser preenchidos!");
            return;
        }

        try {
            PistaDto dto = (pistaEdicao == null) ? new PistaDto() : pistaEdicao;
            dto.setNome(campoNome.getText());
            dto.setLocalizacao(campoLocal.getText());
            dto.setExtensaoMetros(Integer.parseInt(campoDistancia.getText()));

            String metodo = (pistaEdicao == null) ? "POST" : "PUT";
            pistaService.salvarPista(dto, metodo);

            fecharJanela(event);
        }
        catch (NumberFormatException exception) {
            mostrarAlerta("Erro", "A distância deve ser um número inteiro!");
        }
        catch (Exception exception) {
            mostrarAlerta("Erro", "Erro ao salvar pista: " + exception.getMessage());
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
}
