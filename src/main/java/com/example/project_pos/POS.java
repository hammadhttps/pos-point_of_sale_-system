package com.example.project_pos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
// mo
public class POS extends Application {
    @Override

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(POS.class.getResource("/com/example/project_pos/vendors.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Loading Page");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}