package menu;

import engine.Engine;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;


// goal: consolidate relic and runes screen setup, avoid duplicate code
public class AdventureSetupScreen {
    private Stage stage;
    private Engine engine;
    private VBox layout;

    // Configurable properties
    private String titleText;
    private String startButtonText;
    private String realmName;

    // The constructor now accepts the specific details for the game mode
    public AdventureSetupScreen(Stage stage, Engine engine, String titleText, String startButtonText, String realmName) {
        this.stage = stage;
        this.engine = engine;
        this.titleText = titleText;
        this.startButtonText = startButtonText;
        this.realmName = realmName;
        createLayout();
    }

    private void createLayout() {
        Label label = new Label(titleText);
        label.setStyle("-fx-font-size: 20px;");

        Button startButton = new Button(startButtonText);
        Button backButton = new Button("Return to Menu");

        // Start button launches the specific realm passed in the constructor
        startButton.setOnAction(e -> {
            String mapToLaunch = realmName;

            // If the realm is set to RANDOM, pick one from the array
            if (realmName.equals("RANDOM")) {
                String[] availableMaps = {"FOREST", "LAVA", "ICE", "DESERT", "MUD"};
                int randomIndex = new Random().nextInt(availableMaps.length);
                mapToLaunch = availableMaps[randomIndex];

                System.out.println("Randomly selected map: " + mapToLaunch); // Good for debugging
            }

            engine.launchMiniAdventure(mapToLaunch);
        });

        // Back button logic stays the same
        backButton.setOnAction(e -> {
            MainMenuScreen menu = new MainMenuScreen(stage, engine);
            stage.getScene().setRoot(menu.getLayout());
        });

        layout = new VBox(20);
        layout.getChildren().addAll(label, startButton, backButton);
        layout.setAlignment(Pos.CENTER);
    }

    public VBox getLayout() {
        return layout;
    }
}