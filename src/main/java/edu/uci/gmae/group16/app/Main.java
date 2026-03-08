package edu.uci.gmae.group16.app;

import edu.uci.gmae.group16.gui.MainMenuScreen;
import edu.uci.gmae.group16.engine.Setup;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        MainMenuScreen mainMenuScreen = new MainMenuScreen(stage);
        mainMenuScreen.run();

        Setup.init();
    }

    public static void main(String[] args) {
        launch();
    }
}

