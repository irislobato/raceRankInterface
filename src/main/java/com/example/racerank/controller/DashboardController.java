package com.example.racerank.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import javax.swing.*;
import javafx.event.ActionEvent;
import java.io.IOException;


public class DashboardController {
    //ligando o controller ao main_dashboard
    @FXML
    private BorderPane painelPrincipal;

    //função para colocar uma nova tela no centro do dashboard
    private void carregarTela(String arquivoFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/racerank/" + arquivoFxml));
            Parent novaTela = loader.load();

            String cssPath = getClass().getResource("/com/example/racerank/style.css").toExternalForm();
            if (cssPath != null) {
                novaTela.getStylesheets().add(cssPath);
            }

            painelPrincipal.setCenter(novaTela);
        } catch (IOException exception) {
            System.out.println("Erro ao carregar o arquivo FXML: " + arquivoFxml);
            exception.printStackTrace();
        }
    }

    //ação dos botões laterais
    //Dashboard
    @FXML
    public void abrirDashboard(ActionEvent event){
        //O dashboard é a tela inicial, logo, recarregamos um boas-vindas
        System.out.println("Botão Dashboard clicado!");
        carregarTela("tela_home.fxml");
    }

    //Pilotos
    @FXML
    public void abrirPilotos(ActionEvent event){
        System.out.println("Tela de Pilotoa em construção, aguarde um momento...");
        //carrega o arquivo tela_pilotos no meio da tela
        carregarTela("tela_pilotos.fxml");
    }

    //Pistas
    @FXML
    public void abrirPistas(ActionEvent event){
        System.out.println("Tela de Pistas em construção, aguarde um momento...");
        carregarTela("tela_pistas.fxml");
    }

    //Registro
    @FXML
    public void abrirRegistro(ActionEvent event){
        System.out.println("Tela de Registro em construção, aguarde um momento...");
        carregarTela("tela_registro.fxml");
    }

    //Registro
    @FXML
    public void abrirRanking(ActionEvent event){
        System.out.println("Tela de Ranking em construção, aguarde um momento...");
        carregarTela("tela_ranking.fxml");
    }
}
