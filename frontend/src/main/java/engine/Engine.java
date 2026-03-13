package engine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import menu.MainMenuScreen;

import javax.swing.SwingUtilities;

public class Engine extends Application {
    private BorderPane mainLayout;

    public static void main(String[] args) {
        launch(args); // This launches the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GuildQuest Mini-Adventure Environment");

        // Initialize backend logic
        Setup.init();

        mainLayout = new BorderPane();

        // Pass 'this' (the Engine) to the menu
        MainMenuScreen menu = new MainMenuScreen(primaryStage, this);

        mainLayout.setCenter(menu.getLayout());

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void launchMiniAdventure(String realmName) {
        // Create the JavaFX SwingNode
        SwingNode swingNode = new SwingNode();

        // Create the Swing GamePanel on the Swing Thread
        SwingUtilities.invokeLater(() -> {
            GamePanel gamePanel = new GamePanel(realmName);
            swingNode.setContent(gamePanel); // Put the game panel inside the portal
            gamePanel.startGameThread(); // Start the tutorial's game loop

            Platform.runLater(() -> {
                mainLayout.setCenter(swingNode);
                Stage stage = (Stage) mainLayout.getScene().getWindow();
                stage.sizeToScene();
                stage.centerOnScreen(); // Recenter the window
            });
        });

        // Give the game panel focus so WASD/arrow keys work
        swingNode.requestFocus();
    }
}
