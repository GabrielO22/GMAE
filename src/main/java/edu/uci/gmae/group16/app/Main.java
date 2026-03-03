package edu.uci.gmae.group16.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("GMAE Group 16");
        stage.setScene(new Scene(new Label("GMAE is running"), 400, 200));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

