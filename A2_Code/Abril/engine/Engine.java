package engine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.SwingUtilities;

public class Engine extends Application {
    private BorderPane mainLayout;

    public static void main(String[] args) {
        launch(args); // This launches the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GuildQuest Mini-Adventure Environment");

        // 1. Create the main JavaFX layout
        mainLayout = new BorderPane();
        mainLayout.setPrefSize(800, 600);

        // 2. Create a simple JavaFX Main Menu
        VBox mainMenu = new VBox(10);
        mainMenu.setAlignment(javafx.geometry.Pos.CENTER); // center the button
        Button startDungeonRaceBtn = new Button("Start Relic Hunt!");

        // 3. CLick button
        startDungeonRaceBtn.setOnAction(e -> launchMiniAdventure());

        mainMenu.getChildren().add(startDungeonRaceBtn);
        mainLayout.setCenter(mainMenu);

        // 4. Show the JavaFX Window
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Close background threads when the window is 'X'd out
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }

    private void launchMiniAdventure() {
        // 1. Create the JavaFX SwingNode
        SwingNode swingNode = new SwingNode();

        // 2. Create the Swing GamePanel on the Swing Thread
        SwingUtilities.invokeLater(() -> {
            GamePanel gamePanel = new GamePanel();
            swingNode.setContent(gamePanel); // Put the game panel inside the portal
            gamePanel.startGameThread(); // Start the tutorial's game loop

            Platform.runLater(() -> {
                mainLayout.setPrefSize(javafx.scene.layout.Region.USE_COMPUTED_SIZE, javafx.scene.layout.Region.USE_COMPUTED_SIZE);

                Stage stage = (Stage) mainLayout.getScene().getWindow();
                stage.sizeToScene();
                stage.centerOnScreen(); // Recenter the window
            });
        });

        // 3. Swap the JavaFX view from the Main Menu to the Game
        mainLayout.setCenter(swingNode);

        // Give the game panel focus so WASD/arrow keys work
        swingNode.requestFocus();
    }
}
