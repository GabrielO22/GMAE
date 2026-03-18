package menu;

import characters.Character;
import characters.CharacterRegistry;
import characters.CharacterType;
import engine.Engine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import profiles.PlayerProfile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharacterSelectScreen {
    private final BorderPane layout; // Made final so we never overwrite the active window
    private final Engine engine;

    private int currentPlayerDrafting = 1;
    private List<Character> currentDraftList = new ArrayList<>();

    private final PlayerProfile p1Profile;
    private final PlayerProfile p2Profile;

    private Label titleLabel;
    private Label selectionStatusLabel;
    private Button confirmButton;

    public CharacterSelectScreen(Engine engine) {
        this.engine = engine;
        this.p1Profile = new PlayerProfile();
        this.p2Profile = new PlayerProfile();
        p1Profile.setPlayerName("Player 1");
        p2Profile.setPlayerName("Player 2");

        // Initialize the base layout ONCE
        Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 14);
        layout = new BorderPane();
        layout.setStyle("-fx-background-color: #232323;");
        layout.setPadding(new Insets(15, 30, 15, 30));

        setupStaticUI();
        refreshRosterBoard(); // Populates the cards for the first time
    }

    // Builds the Top and Bottom UI that stays on the screen
    private void setupStaticUI() {
        // --- TOP: TITLE ---
        titleLabel = new Label("Player" + currentPlayerDrafting + ": Select your characters");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #EEEEEE; -fx-font-family: 'Press Start 2P';");

        selectionStatusLabel = new Label("Selected: 0 / 3");
        selectionStatusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0B6B80; -fx-font-family: 'Press Start 2P';");

        VBox topBox = new VBox(5, titleLabel, selectionStatusLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(0, 0, 10, 0));
        layout.setTop(topBox);

        // --- BOTTOM: CONFIRM BUTTON ---
        confirmButton = new Button("Confirm Team");
        confirmButton.setDisable(true);
        confirmButton.setStyle("-fx-font-size: 16px; -fx-padding: 8 30; -fx-background-color: #1B5B1D; -fx-text-fill: #EEEEEE; -fx-font-weight: bold; -fx-font-family: 'Press Start 2P';");

        confirmButton.setOnAction(e -> handleConfirmTeam());

        HBox bottomBox = new HBox(confirmButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15, 0, 0, 0));
        layout.setBottom(bottomBox);
    }

    // Clears and rebuilds ONLY the center cards. Fixes the Player 2 frozen screen bug!
    private void refreshRosterBoard() {
        // Strict 2x3 Grid definition
        GridPane rosterGrid = new GridPane();
        rosterGrid.setAlignment(Pos.CENTER);
        rosterGrid.setHgap(20); // Horizontal gap between cards
        rosterGrid.setVgap(15); // Vertical gap between rows

        int col = 0;
        int row = 0;

        for (CharacterType type : CharacterRegistry.getAllClasses()) {
            rosterGrid.add(createCharacterCard(type), col, row);

            col++;
            // Once we hit 3 columns, reset to 0 and move to the next row
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        // Put the newly built grid into the center of our existing layout
        layout.setCenter(rosterGrid);
    }

    private VBox createCharacterCard(CharacterType type) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10));

        // Classic retro blue box with a thick white border
        card.setStyle(
            "-fx-background-color: #000080; " + // Deep navy blue
            "-fx-border-color: #FFFFFF; " +     // Pure white border
            "-fx-border-width: 4; " +           // Thick, blocky border
            "-fx-border-radius: 0; " +          // NO rounded corners!
            "-fx-background-radius: 0;"         // NO rounded corners!
        );
        card.setPrefWidth(210); // Defined width for consistency

        // --- IMAGE PLACEHOLDER ---
        ImageView spriteView;
        try {
            spriteView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(type.getSpritePath()))));
        } catch (Exception e) {
            System.err.println("Could not load image: " + type.getSpritePath());
            spriteView = new ImageView(); // Fallback if path is wrong
        }
        spriteView.setFitWidth(48);
        spriteView.setFitHeight(48);
        spriteView.setSmooth(false); // CRITICAL: Keeps pixel art sharp!

        Label nameLabel = new Label(type.getDisplayName());
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0B6B80; -fx-font-family: 'Press Start 2P';");

        // --- STATS GRID ---
        GridPane statsGrid = getStatsGrid(type);

        // --- DRAFT / UNDO BUTTON ---
        Button draftButton = getDraftButton(type, card);

        card.getChildren().addAll(spriteView, nameLabel, statsGrid, draftButton);
        return card;
    }

    private Button getDraftButton(CharacterType type, VBox card) {
        Button draftButton = new Button("Draft");
        draftButton.setStyle(
            "-fx-background-color: #555555; " +
            "-fx-border-color: #FFFFFF; " +
            "-fx-border-width: 2; " +
            "-fx-text-fill: #FFFFFF; " +
            "-fx-font-weight: bold; " +
            "-fx-cursor: hand; " +
            "-fx-font-family: 'Press Start 2P';"
        );

        draftButton.setOnAction(e -> {
            Character existingDraft = null;
            for (Character c : currentDraftList) {
                if (c.getName().equals(type.getDisplayName())) {
                    existingDraft = c;
                    break;
                }
            }

            if (existingDraft != null) {
                // UNDO LOGIC: Revert to unselected Retro Blue style
                currentDraftList.remove(existingDraft);

                card.setStyle(
                    "-fx-background-color: #000080; " +
                    "-fx-border-color: #FFFFFF; " +
                    "-fx-border-width: 4; " +
                    "-fx-border-radius: 0; " +
                    "-fx-background-radius: 0;"
                );

                draftButton.setText("Draft");
                draftButton.setStyle(
                    "-fx-background-color: #555555; " +
                    "-fx-border-color: #FFFFFF; " +
                    "-fx-border-width: 2; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-font-weight: bold; " +
                    "-fx-cursor: hand; " +
                    "-fx-font-family: 'Press Start 2P';"
                );
            } else {
                // DRAFT LOGIC: Change to selected Retro Yellow style
                if (currentDraftList.size() < 3) {
                    Character newChar = CharacterRegistry.createCharacter(type.getDisplayName(), type.getDisplayName());
                    currentDraftList.add(newChar);

                    // Notice the Yellow (#FFFF00) border here!
                    card.setStyle(
                        "-fx-background-color: #000080; " +
                        "-fx-border-color: #FFFF00; " +
                        "-fx-border-width: 4; " +
                        "-fx-border-radius: 0; " +
                        "-fx-background-radius: 0;"
                    );

                    draftButton.setText("Undo");
                    draftButton.setStyle(
                        "-fx-background-color: #8c2a2a; " + // Red background
                        "-fx-border-color: #FFFFFF; " +      // Kept the thick white border
                        "-fx-border-width: 2; " +
                        "-fx-text-fill: #FFFFFF; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand; " +
                        "-fx-font-family: 'Press Start 2P';"
                    );
                }
            }
            updateStatus();
        });
        return draftButton;
    }

    private static GridPane getStatsGrid(CharacterType type) {
        Label hpLabel = new Label("HP: " + type.getMaxHP());
        Label atkLabel = new Label("ATK: " + type.getAttack());
        Label defLabel = new Label("DEF: " + type.getDefence());
        Label spdLabel = new Label("SPD: " + type.getSpeed());

        String statStyle = "-fx-text-fill: #EEEEEE; -fx-font-size: 11px; -fx-font-family: 'Press Start 2P';";
        hpLabel.setStyle(statStyle);
        atkLabel.setStyle(statStyle);
        defLabel.setStyle(statStyle);
        spdLabel.setStyle(statStyle);

        GridPane statsGrid = new GridPane();
        statsGrid.setAlignment(Pos.CENTER);
        statsGrid.setHgap(15);
        statsGrid.setVgap(5);
        statsGrid.add(hpLabel, 0, 0);
        statsGrid.add(atkLabel, 1, 0);
        statsGrid.add(defLabel, 0, 1);
        statsGrid.add(spdLabel, 1, 1);
        return statsGrid;
    }

    private void updateStatus() {
        selectionStatusLabel.setText("Selected: " + currentDraftList.size() + " / 3");
        confirmButton.setDisable(currentDraftList.size() < 3);
    }

    private void handleConfirmTeam() {
        if (currentPlayerDrafting == 1) {
            p1Profile.getCharacters().clear();
            p1Profile.getCharacters().addAll(currentDraftList);

            currentDraftList.clear();
            currentPlayerDrafting = 2;

            titleLabel.setText("Player" + currentPlayerDrafting + ": Select your characters");
            updateStatus();

            // Re-render ONLY the cards! This guarantees the buttons work for Player 2
            refreshRosterBoard();

        } else {
            p2Profile.getCharacters().clear();
            p2Profile.getCharacters().addAll(currentDraftList);

            System.out.println("Draft Complete! Sending to Main Menu...");
            engine.launchMainMenu(p1Profile, p2Profile);
        }
    }

    public BorderPane getLayout() {
        return layout;
    }
}