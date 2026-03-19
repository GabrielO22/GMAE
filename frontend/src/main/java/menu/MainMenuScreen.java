package menu;

import engine.Engine;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import music.MusicPlayer;
import utils.Constants;

public class MainMenuScreen {
    private Stage stage;
    private Engine engine;
    private VBox layout;

    public MainMenuScreen(Stage stage, Engine engine) {
        this.stage = stage;
        this.engine = engine;
        createLayout();
        MusicPlayer.play(Constants.TRACK_MENU);
    }

    private void createLayout() {
        layout = new VBox(30); // Increased spacing to 30px
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(768, 576); // screen size from gamepanel

        // ADDED RETRO BACKGROUND
        layout.setStyle("-fx-background-color: #232323;");

        // ADDED RETRO STYLING TO LABELS
        Label title = new Label("MAIN MENU");
        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #EEEEEE; -fx-font-family: 'Press Start 2P';");

        Label subtitle = new Label("Select Your Quest");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #0B6B80; -fx-font-family: 'Press Start 2P';");

        // GENERATE RETRO BUTTONS
        Button itemCollectionButton = createRetroButton("Realm Relic Run");
        Button turnDuelButton = createRetroButton("Runes of Reckoning");
        Button profileButton = createRetroButton("Player Profiles");

        // When clicked, it tells Engine to launch the correct Setup Screen
        itemCollectionButton.setOnAction(e -> {
            RelicHuntSetupScreen relicScreen = new RelicHuntSetupScreen(
                    stage, engine, "Realm Relic Run Setup", "Start Game!", "RANDOM"
            );
            stage.getScene().setRoot(relicScreen.getLayout());
        });

        turnDuelButton.setOnAction(e -> {
            BattleSetupScreen duelScreen = new BattleSetupScreen(
                    stage, engine, "Runes of Reckoning Setup", "Start Duel!", "LAVA"
            );
            stage.getScene().setRoot(duelScreen.getLayout());
        });

        profileButton.setOnAction(e -> {
            PlayerProfileManager profileManager = new PlayerProfileManager(stage, engine);
            stage.getScene().setRoot(profileManager.getLayout());
        });

        layout.getChildren().addAll(title, subtitle, itemCollectionButton, turnDuelButton, profileButton);
    }

    // Helper method to keep our button styling clean and consistent
    private Button createRetroButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(350); // Made wider to accommodate the pixel font
        btn.setStyle(
            "-fx-background-color: #555555; " +
            "-fx-border-color: #FFFFFF; " +
            "-fx-border-width: 4; " +
            "-fx-text-fill: #FFFFFF; " +
            "-fx-font-size: 16px; " +
            "-fx-padding: 15 20; " +
            "-fx-font-family: 'Press Start 2P'; " +
            "-fx-cursor: hand;"
        );

        // Add a Teal hover effect
        btn.setOnMouseEntered(e -> btn.setStyle(btn.getStyle().replace("#555555", "#0B6B80")));
        btn.setOnMouseExited(e -> btn.setStyle(btn.getStyle().replace("#0B6B80", "#555555")));

        return btn;
    }

    public VBox getLayout() {
        return layout;
    }
}