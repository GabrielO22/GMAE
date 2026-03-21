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

public class RelicHuntSetupScreen {
    private final Stage stage;
    private final Engine engine;
    private final BorderPane layout;

    private final String titleText;
    private final String startButtonText;
    private final String realmName;

    private Character selectedP1Character = null;
    private Character selectedP2Character = null;
    private Button startButton;

    public RelicHuntSetupScreen(Stage stage, Engine engine, String titleText, String startButtonText, String realmName) {
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
        // --- TOP: TITLE ONLY ---
        Label titleLabel = new Label(titleText);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #EEEEEE; -fx-font-family: 'Press Start 2P';");

        VBox topBox = new VBox(titleLabel); // Removed the back button from here
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(0, 0, 20, 0));
        layout.setTop(topBox);

        // --- CENTER: SELECTION AREA ---
        HBox selectionArea = new HBox(50);
        selectionArea.setAlignment(Pos.CENTER);
        selectionArea.getChildren().addAll(
                buildPlayerColumn("Player 1", engine.getPlayer1Profile().getCharacters(), 1),
                buildPlayerColumn("Player 2", engine.getPlayer2Profile().getCharacters(), 2)
        );
        layout.setCenter(selectionArea);

        // --- BOTTOM: START & BACK BUTTONS ---
        startButton = new Button(startButtonText);
        startButton.setDisable(true);
        startButton.setStyle("-fx-font-size: 18px; -fx-padding: 10 30; -fx-background-color: #1B5B1D; -fx-border-color: #FFFFFF; -fx-border-width: 4; -fx-text-fill: #EEEEEE; -fx-font-family: 'Press Start 2P'; -fx-cursor: hand;");

        startButton.setOnAction(e -> {
            String mapToLaunch = realmName;
            if (realmName.equals("RANDOM")) {
                String[] availableMaps = {"FOREST", "LAVA", "ICE", "DESERT", "MUD"};
                mapToLaunch = availableMaps[new Random().nextInt(availableMaps.length)];
            }
            engine.launchMiniAdventure(mapToLaunch, selectedP1Character, selectedP2Character);
        });

        // Moved Back Button down here!
        VBox bottomButton = getBottomButton();
        layout.setBottom(bottomButton);
    }

    private VBox getBottomButton() {
        Button backButton = new Button("Return to Menu");
        backButton.setStyle("-fx-background-color: #555555; -fx-border-color: #FFFFFF; -fx-border-width: 2; -fx-text-fill: #FFFFFF; -fx-font-family: 'Press Start 2P'; -fx-cursor: hand;");
        backButton.setOnAction(e -> stage.getScene().setRoot(new MainMenuScreen(stage, engine).getLayout()));

        // Changed to VBox to stack the buttons vertically
        VBox bottomBox = new VBox(15, startButton, backButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 0, 0, 0));
        return bottomBox;
    }

    private VBox buildPlayerColumn(String playerName, List<Character> draftedTeam, int playerNum) {
        VBox column = new VBox(15);
        column.setAlignment(Pos.TOP_CENTER);

        Label playerLabel = new Label(playerName + " Select:");
        playerLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #0B6B80; -fx-font-family: 'Press Start 2P';");
        column.getChildren().add(playerLabel);

        for (Character character : draftedTeam) {
            VBox card = new VBox(5);
            card.setAlignment(Pos.CENTER);
            card.setPadding(new Insets(10));

            // WIDENED CARD from 200 to 240
            card.setPrefWidth(240);

            String defaultStyle = "-fx-background-color: #000080; -fx-border-color: #FFFFFF; -fx-border-width: 4; -fx-cursor: hand;";
            String selectedStyle = "-fx-background-color: #000080; -fx-border-color: #FFFF00; -fx-border-width: 4; -fx-cursor: hand;";
            card.setStyle(defaultStyle);

            ImageView spriteView;
            try {
                spriteView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(character.getClassType().getSpritePath()))));
            } catch (Exception e) {
                spriteView = new ImageView();
            }
            spriteView.setFitWidth(48);
            spriteView.setFitHeight(48);
            spriteView.setSmooth(false);

            Label nameLabel = new Label(character.getName());
            nameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #0B6B80; -fx-font-family: 'Press Start 2P';");

            Label classLabel = new Label("Class: " + character.getClassType().getDisplayName());
            classLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #FFFFFF; -fx-font-family: 'Press Start 2P';");

            Label speedLabel = new Label("Spd: " + character.getClassType().getSpeed());
            speedLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #FFFFFF; -fx-font-family: 'Press Start 2P';");

            VBox statsBox = new VBox(3);
            statsBox.setAlignment(Pos.CENTER);
            statsBox.getChildren().addAll(classLabel, speedLabel);

            card.getChildren().addAll(spriteView, nameLabel, statsBox);

            card.setOnMouseClicked(e -> {
                if (playerNum == 1) selectedP1Character = character;
                else selectedP2Character = character;

                for (int i = 1; i < column.getChildren().size(); i++) column.getChildren().get(i).setStyle(defaultStyle);
                card.setStyle(selectedStyle);
                checkStartCondition();
            });

            column.getChildren().add(card);
        }
        return column;
    }

    private void checkStartCondition() {
        if (selectedP1Character != null && selectedP2Character != null) {
            startButton.setDisable(false);
        }
    }

    public BorderPane getLayout() { return layout; }
}