package menu;

import characters.Character;
import engine.Engine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class BattleSetupScreen {
    private final Stage stage;
    private final Engine engine;
    private final BorderPane layout;

    private final String titleText;
    private final String startButtonText;
    private final String realmName;

    private Button startButton;

    public BattleSetupScreen(Stage stage, Engine engine, String titleText, String startButtonText, String realmName) {
        this.stage = stage;
        this.engine = engine;
        this.titleText = titleText;
        this.startButtonText = startButtonText;
        this.realmName = realmName;

        Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 14);

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

        // --- CENTER: Team previews + VS graphic ---
        VBox p1Team = buildTeamPreview(engine.player1Profile.getPlayerName(),
                engine.player1Profile.getCharacters());
        VBox p2Team = buildTeamPreview(engine.player2Profile.getPlayerName(),
                engine.player2Profile.getCharacters());

        Label vsLabel = new Label("VS");
        vsLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #FFFF00;" +
                " -fx-font-family: 'Press Start 2P';");
        vsLabel.setEffect(new javafx.scene.effect.DropShadow(10, javafx.scene.paint.Color.web("#FF8800")));

        HBox centerRow = new HBox(40, p1Team, vsLabel, p2Team);
        centerRow.setAlignment(Pos.CENTER);
        layout.setCenter(centerRow);

        // --- TICKER: scrolling battle phrases ---
        layout.setBottom(new VBox(0, buildTicker(), buildBottomBar()));
    }

    /**
     * Scrolling ticker bar — phrases scroll right to left, run off screen, then
     * a new random phrase begins. Easy to edit: just update TICKER_PHRASES below.
     */
    private static final String[] TICKER_PHRASES = {
            "⚔  Two warriors enter the arena... only one will leave standing.",
            "🏆  Glory awaits the victor — and ruin awaits the defeated.",
            "🔥  Your relics have been collected. Now spend them wisely.",
            "💀  Choose your moves carefully — one wrong turn could cost everything.",
            "⚡  The crowd grows restless... let the duel begin!"
    };

    private javafx.scene.layout.Pane buildTicker() {
        javafx.scene.layout.Pane tickerPane = new javafx.scene.layout.Pane();
        tickerPane.setPrefHeight(36);
        tickerPane.setStyle("-fx-background-color: transparent;");
        tickerPane.setClip(new javafx.scene.shape.Rectangle(768, 36));

        Label tickerLabel = new Label();
        tickerLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #FFFF00;" +
                " -fx-font-family: 'Press Start 2P';");
        tickerLabel.setLayoutY(8);
        tickerPane.getChildren().add(tickerLabel);

        final int[] phraseIndex = { (int)(Math.random() * TICKER_PHRASES.length) };
        tickerLabel.setText(TICKER_PHRASES[phraseIndex[0]]);

        // Start off-screen RIGHT, scroll left
        tickerLabel.setLayoutX(780);

        javafx.animation.AnimationTimer timer = new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                double x = tickerLabel.getLayoutX() - 2; // scroll left
                tickerLabel.setLayoutX(x);

                // Once fully off-screen left, pick next phrase and reset to right
                if (x + tickerLabel.getWidth() < 0) {
                    phraseIndex[0] = (phraseIndex[0] + 1 + (int)(Math.random() *
                            (TICKER_PHRASES.length - 1))) % TICKER_PHRASES.length;
                    tickerLabel.setText(TICKER_PHRASES[phraseIndex[0]]);
                    tickerLabel.setLayoutX(780);
                }
            }
        };
        timer.start();

        // Stop the timer when the node is removed from the scene
        tickerPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) timer.stop();
        });

        return tickerPane;
    }

    private HBox buildBottomBar() {
        // --- BOTTOM: START BUTTON ---
        startButton = new Button(startButtonText);
        startButton.setStyle("-fx-font-size: 18px; -fx-padding: 10 30; -fx-background-color: #1B5B1D;" +
                " -fx-border-color: #FFFFFF; -fx-border-width: 4; -fx-text-fill: #EEEEEE;" +
                " -fx-font-family: 'Press Start 2P';");
        startButton.setOnAction(e -> engine.launchDuel());

        HBox bottomBox = new HBox(startButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 0, 0, 0));
        return bottomBox;
    }

    private VBox buildTeamPreview(String playerName, List<Character> team) {
        VBox column = new VBox(12);
        column.setAlignment(Pos.TOP_CENTER);
        column.setPrefWidth(300);

        Label nameLabel = new Label(playerName != null ? playerName : "Player");
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0B6B80;" +
                " -fx-font-family: 'Press Start 2P';");
        column.getChildren().add(nameLabel);

        for (Character c : team) {
            VBox card = new VBox(5);
            card.setAlignment(Pos.CENTER_LEFT);
            card.setPadding(new Insets(8, 12, 8, 12));
            card.setStyle("-fx-background-color: #000080; -fx-border-color: #FFFFFF;" +
                    " -fx-border-width: 3; -fx-border-radius: 0; -fx-background-radius: 0;");

            // Sprite + name on one row
            ImageView sprite = new ImageView();
            sprite.setFitWidth(32);
            sprite.setFitHeight(32);
            sprite.setSmooth(false);
            if (c.getClassType() != null) {
                try {
                    sprite.setImage(new Image(Objects.requireNonNull(
                            getClass().getResourceAsStream(c.getClassType().getSpritePath()))));
                } catch (Exception ignored) {}
            }

            Label charName = new Label(c.getName().isEmpty() ? "Unknown" : c.getName());
            charName.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #FFFF00;" +
                    " -fx-font-family: 'Press Start 2P';");

            HBox nameRow = new HBox(8, sprite, charName);
            nameRow.setAlignment(Pos.CENTER_LEFT);

            // Stats row
            String className = c.getClassType() != null ? c.getClassType().getDisplayName() : "?";
            Label classLabel = new Label("Class: " + className);
            classLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #AAAAAA; -fx-font-family: 'Press Start 2P';");

            Label statsLabel = new Label("HP:" + c.getMaxHP() + "  ATK:" + c.getAttack() + "  DEF:" + c.getDefence());
            statsLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #AAAAAA; -fx-font-family: 'Press Start 2P';");

            card.getChildren().addAll(nameRow, classLabel, statsLabel);
            column.getChildren().add(card);
        }

        return column;
    }

    public BorderPane getLayout() {
        return layout;
    }
}