package com.example.racerank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        //barra lateral
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main_dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);

        stage.setTitle("RaceRank - Ssitema de Gerenciamento de Corridas");
        stage.setScene(scene);

        //abrir em tela cheia
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
