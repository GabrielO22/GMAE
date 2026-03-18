package menu;

import engine.Engine;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TitleScreen {
    private final VBox layout;
    private final Engine engine;

    public TitleScreen(Engine engine) {
        this.engine = engine;

        // Make sure the font is loaded!
        Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 14);

        layout = new VBox(40); // 40px spacing between elements
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #000000;"); // Classic black retro background

        setupUI();
    }

    private void setupUI() {
        // --- GAME TITLE ---
        Label titleLabel = new Label("GUILD QUEST");
        titleLabel.setStyle(
                "-fx-font-size: 60px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #FFFF00; " + // Retro Yellow
                        "-fx-font-family: 'Press Start 2P';"
        );

        Label subtitleLabel = new Label("Mini-Adventure Environment");
        subtitleLabel.setStyle(
                "-fx-font-size: 18px; " +
                        "-fx-text-fill: #FFFFFF; " +
                        "-fx-font-family: 'Press Start 2P';"
        );

        // --- START BUTTON ---
        Button startButton = new Button("PRESS START");
        startButton.setStyle(
                "-fx-background-color: #2b2b2b; " +
                        "-fx-border-color: #FFFFFF; " +
                        "-fx-border-width: 4; " +
                        "-fx-text-fill: #FFFFFF; " +
                        "-fx-font-size: 24px; " +
                        "-fx-padding: 15 30; " +
                        "-fx-font-family: 'Press Start 2P'; " +
                        "-fx-cursor: hand;"
        );

        // Hover effect to make it feel responsive
        startButton.setOnMouseEntered(e -> startButton.setStyle(startButton.getStyle().replace("#2b2b2b", "#555555")));
        startButton.setOnMouseExited(e -> startButton.setStyle(startButton.getStyle().replace("#555555", "#2b2b2b")));

        // When clicked, tell the Engine to launch the Draft Screen!
        startButton.setOnAction(e -> engine.launchDraftScreen());

        layout.getChildren().addAll(titleLabel, subtitleLabel, startButton);
    }

    public VBox getLayout() {
        return layout;
    }
}