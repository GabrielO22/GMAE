package menu;

import characters.Character;
import engine.Engine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BattleSetupScreen {
    private final Stage stage;
    private final Engine engine;
    private final BorderPane layout;

    private final String titleText;
    private final String startButtonText;
    private final String realmName;

    private Character selectedP1Character = null;
    private Character selectedP2Character = null;
    private Button startButton;

    public BattleSetupScreen(Stage stage, Engine engine, String titleText, String startButtonText, String realmName) {
        this.stage = stage;
        this.engine = engine;
        this.titleText = titleText;
        this.startButtonText = startButtonText;
        this.realmName = realmName;

        layout = new BorderPane();
        layout.setStyle("-fx-background-color: #232323;");
        layout.setPadding(new Insets(20));

        createLayout();
    }

    private void createLayout() {
        // --- TOP: TITLE & BACK BUTTON ---
        Label titleLabel = new Label(titleText);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #EEEEEE; -fx-font-family: 'Press Start 2P';");

        Button backButton = new Button("Return to Menu");
        backButton.setStyle("-fx-background-color: #555555; -fx-border-color: #FFFFFF; -fx-border-width: 2; -fx-text-fill: #FFFFFF; -fx-font-family: 'Press Start 2P'; -fx-cursor: hand;");
        backButton.setOnAction(e -> {
            MainMenuScreen menu = new MainMenuScreen(stage, engine);
            stage.getScene().setRoot(menu.getLayout());
        });

        VBox topBox = new VBox(15, titleLabel, backButton);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(0, 0, 20, 0));
        layout.setTop(topBox);


        // --- BOTTOM: START BUTTON ---
        startButton = new Button(startButtonText);
        startButton.setDisable(false); // Profiles already set from character draft
        startButton.setStyle("-fx-font-size: 18px; -fx-padding: 10 30; -fx-background-color: #1B5B1D; -fx-border-color: #FFFFFF; -fx-border-width: 4; -fx-text-fill: #EEEEEE; -fx-font-family: 'Press Start 2P';");

        startButton.setOnAction(e -> {
            System.out.println("Launching Turn-Based Battle with "
                    + engine.player1Profile.getPlayerName()
                    + " vs "
                    + engine.player2Profile.getPlayerName());
            engine.launchDuel();
        });

        HBox bottomBox = new HBox(startButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 0, 0, 0));
        layout.setBottom(bottomBox);
    }


    public BorderPane getLayout() {
        return layout;
    }
}