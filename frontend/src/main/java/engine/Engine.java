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
    private Stage window;

    public static void main(String[] args) {
        launch(args); // This launches the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        this.window = primaryStage;
        primaryStage.setTitle("GuildQuest Mini-Adventure Environment");

        // Initialize backend logic
        Setup.init();

        mainLayout = new BorderPane();

        // Pass 'this' (the Engine) to the menu
        MainMenuScreen menu = new MainMenuScreen(primaryStage, this);

        mainLayout.setCenter(menu.getLayout());

        Scene scene = new Scene(mainLayout, 768, 576); // screen settings from game panel
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Closing application...");
            System.exit(0); // This forcefully terminates the JVM
        });

        primaryStage.show();
    }

    public void launchMiniAdventure(String realmName) {
        // Create the JavaFX SwingNode
        SwingNode swingNode = new SwingNode();

        // Create the Swing GamePanel on the Swing Thread
        SwingUtilities.invokeLater(() -> {
            try {
                GamePanel gamePanel = new GamePanel(realmName);
                swingNode.setContent(gamePanel); // Put the game panel inside the portal
                gamePanel.startGameThread(); // Start the tutorial's game loop

                Platform.runLater(() -> {
                    // Put main layout back on screen
                    window.getScene().setRoot(mainLayout);
                    mainLayout.setCenter(swingNode);

                    window.sizeToScene();
                    window.centerOnScreen(); // Recenter the window using saved window
                });
            } catch (Exception ex) { // <-- ADD THIS CATCH BLOCK
                System.out.println("CRASH LOADING MAP: " + realmName);
                ex.printStackTrace();
            }
        });

        // Give the game panel focus so WASD/arrow keys work
        swingNode.requestFocus();
    }
}
