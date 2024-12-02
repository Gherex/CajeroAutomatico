package com.gherex.cajeroautomaticojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CajeroApplication extends Application {
    private static Stage primaryStage; // Almacenar referencia al Stage principal

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage; // Guardar el Stage principal
        FXMLLoader fxmlLoader = new FXMLLoader(CajeroApplication.class.getResource("Bienvenida.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cajero Automático");
        stage.setScene(scene);
        stage.show();
    }

    // Para cambiar escenas
    public static void changeScene(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CajeroApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}
