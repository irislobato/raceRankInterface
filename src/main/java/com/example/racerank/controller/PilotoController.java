package com.example.racerank.controller;

import com.example.racerank.dto.PilotoDto;
import com.example.racerank.service.PilotoService;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;

public class PilotoController implements Initializable {

    @FXML
    private TableView<PilotoDto> tabelaPilotos;

    @FXML
    private TableColumn<PilotoDto, Long> colunaId;

    @FXML
    private TableColumn<PilotoDto, String> colunaNome;

    @FXML
    private TableColumn<PilotoDto, String> colunaEquipe;

    @FXML
    private TableColumn<PilotoDto, LocalDateTime> colunaData;

    @FXML
    private TextField campoPesquisa;

    private ObservableList<PilotoDto> listaPilotos = FXCollections.observableArrayList();
    //para manter os dados originais ao filtar
    private ObservableList<PilotoDto> listaOriginal = FXCollections.observableArrayList();
    private PilotoService pilotoService = new PilotoService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //configurações das colunas
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEquipe.setCellValueFactory(new PropertyValueFactory<>("equipe"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));

        //formatação da data
        colunaData.setCellFactory(column -> new TableCell<>() {
            private final java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.format(formatter));
            }
        });

        tabelaPilotos.setItems(listaPilotos);

        //carrega dados do servidor
        carregarDadosDoServidor();

        //campo de pesquisa
        campoPesquisa.textProperty().addListener((obs, antigo, novo) -> filtrarPilotos(novo));

        //aplicando style css
        tabelaPilotos.getStylesheets().add(getClass().getResource("/com/example/racerank/style.css").toExternalForm());
    }

    private void carregarDadosDoServidor() {
        try {
            List<PilotoDto> pilotos = pilotoService.buscarTodosPilotos();
            listaOriginal.setAll(pilotos);
            listaPilotos.setAll(pilotos);
        } catch (Exception exception) {
            System.out.println("Erro ao carregar dados do servidor: " + exception.getMessage());
        }
    }

    private void filtrarPilotos(String texto) {
        if (texto == null || texto.isEmpty()) {
            listaPilotos.setAll(listaOriginal);
        } else {
            String filtro = texto.toLowerCase();
            List<PilotoDto> filtrados = listaOriginal.stream()
                    .filter(pilotoDto -> pilotoDto.getNome().toLowerCase().contains(filtro) ||
                            pilotoDto.getEquipe().toLowerCase().contains(filtro))
                    .collect(Collectors.toList());
            listaPilotos.setAll(filtrados);
        }
    }

    private void abrirModalEdicao(PilotoDto pilotoParaEditar) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/racerank/tela_cadastro_piloto.fxml"));
            Parent root = loader.load();

            CadastroPilotoController controller = loader.getController();
            controller.setPiloto(pilotoParaEditar); //passa o piloto para o formulário

            Stage stage = new Stage();
            stage.setTitle(pilotoParaEditar == null ? "Novo Cadastro" : "Editar Piloto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            carregarDadosDoServidor();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    public void editarPiloto(ActionEvent event) {
        System.out.println("Botão editar cliclado!");
        PilotoDto selecionado = tabelaPilotos.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            System.out.println("Nenhum piloto slecionado!");
            mostrarAlerta("Aviso", "Selecione um piloto para editar!");
            return;
        }
        abrirModalEdicao(selecionado);
    }

    @FXML
    public void deletarPiloto(ActionEvent event) {
        System.out.println("Botão deletar clicado!");
        PilotoDto selecionado = tabelaPilotos.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            System.out.println("Nenhum piloto selecionado!");
            mostrarAlerta("Aviso", "Selecione um piloto para excluir!");
            return;
        }
        try {
            pilotoService.deletarPiloto(selecionado.getId());
            carregarDadosDoServidor(); //atualiza a tabela na tela
        }
        catch (Exception exception) {
            mostrarAlerta("Erro", "Não foi possível excluir o piloto.");
        }
    }
}
