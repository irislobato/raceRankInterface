package com.example.racerank.controller;

import com.example.racerank.dto.PistaDto;
import com.example.racerank.service.PistaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PistaController implements Initializable {

    @FXML private TableView<PistaDto> tabelaPistas;
    @FXML private TableColumn<PistaDto, Long> colunaId;
    @FXML private TableColumn<PistaDto, String> colunaNome;
    @FXML private TableColumn<PistaDto, String> colunaLocal;
    @FXML private TableColumn<PistaDto, Integer> colunaDistancia;
    @FXML private TextField campoPesquisa;

    private ObservableList<PistaDto> listaPistas = FXCollections.observableArrayList();
    private PistaService pistaService = new PistaService();
    private ObservableList<PistaDto> listaOriginal = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaLocal.setCellValueFactory(new PropertyValueFactory<>("localizacao"));
        colunaDistancia.setCellValueFactory(new PropertyValueFactory<>("extensaoMetros"));

        tabelaPistas.setItems(listaPistas);
        carregarPistasDoServidor();

        campoPesquisa.textProperty().addListener((obs, antigo, novo) -> filtrarPistas(novo));
    }

    private void filtrarPistas(String texto) {
        if (texto == null || texto.isEmpty()) {
            listaPistas.setAll(listaOriginal);
        } else {
            String filtro = texto.toLowerCase();
            List<PistaDto> filtrados = listaOriginal.stream()
                    .filter(p -> p.getNome().toLowerCase().contains(filtro) ||
                            p.getLocalizacao().toLowerCase().contains(filtro))
                    .collect(Collectors.toList());
            listaPistas.setAll(filtrados);
        }
    }

    private void carregarPistasDoServidor() {
        try {
            List<PistaDto> pistas = pistaService.buscarTodasPistas();
            listaOriginal.setAll(pistas); // Salva a lista completa
            listaPistas.setAll(pistas);   // Exibe a lista completa
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editarPista(ActionEvent event) {
        PistaDto selecionado = tabelaPistas.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Aviso", "Selecione uma pista para editar!");
            return;
        }
        abrirModalEdicao(selecionado);
    }

    private void abrirModalEdicao(PistaDto pista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/racerank/tela_cadastro_pista.fxml"));
            Parent root = loader.load();
            CadastroPistaController controller = loader.getController();
            controller.setPista(pista);

            Stage stage = new Stage();
            stage.setTitle("Editar Pista");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            carregarPistasDoServidor(); // Atualiza a lista após salvar
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    public void novaPista(ActionEvent event) {
        abrirModalEdicao(null);
    }
}
