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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import music.MusicPlayer;
import profiles.PlayerProfile;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static utils.Constants.AVATAR_PATHS;

public class PlayerProfileManager {
    private final Stage stage;
    private final Engine engine;
    private final BorderPane layout;

    private int activeTab;
    private int activePlayer;
    private Button[] tabs;
    private VBox statsPanel;

    public PlayerProfileManager(Stage stage, Engine engine) {
        this.stage = stage;
        this.engine = engine;
        this.activePlayer = 1;

        layout = new BorderPane();
        layout.setStyle(StyleFactory.createBackground(Constants.BG_MAIN));
        layout.setPadding(new Insets(20));
        layout.setPrefSize(768, 576);

        renderUI();
        MusicPlayer.play(Constants.TRACK_MENU);
    }

    private void renderUI() {
        activeTab = 0;
        layout.setTop(createHeader());
        layout.setCenter(createCenterSection());
        layout.setBottom(createFooter());
    }

    // =============================================
    // HELPERS
    // =============================================
    private PlayerProfile getCurrentProfile() {
        return activePlayer == 1 ? engine.player1Profile : engine.player2Profile;
    }

    private List<Character> getCharacters() {
        return getCurrentProfile().getCharacters();
    }

    // =============================================
    // HEADER
    // =============================================
    private VBox createHeader() {
        Label title = StyleFactory.createLabel("Player Profile Manager", 20, Constants.OFF_WHITE);
        Label name = StyleFactory.createLabel(getCurrentProfile().getPlayerName(), 16, Constants.WHITE);
        Label relics = StyleFactory.createLabel(getCurrentProfile().getRelicsCollectedToString(), 14, Constants.WHITE);
        Label records = StyleFactory.createLabel(getCurrentProfile().getDuelRecord(), 14, Constants.WHITE);

        VBox header = new VBox(12, title, name, relics, records);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 8, 0));
        return header;
    }

    // =============================================
    // CENTER SECTION
    // =============================================
    private VBox createCenterSection() {
        HBox mainCard = createMainProfileCard();
        HBox tabBar = createCharacterTabs();

        VBox center = new VBox(0, tabBar, mainCard);
        center.setAlignment(Pos.CENTER_LEFT);
        return center;
    }

    // =============================================
    // CHARACTER TABS
    // =============================================
    private HBox createCharacterTabs() {
        List<Character> characters = getCharacters();
        tabs = new Button[3];

        for (int i = 0; i < 3; ++i) {
            final int idx = i;
            Button btn = new Button(characters.get(i).getName());
            StyleFactory.applyTabStyle(btn, i == activeTab);

            btn.setOnMouseEntered(e -> {
                boolean sel = (idx == activeTab);
                String hoverBg = sel ? "#555555" : "#BBBBBB";
                String hoverTxt = sel ? Constants.TEAL_ACCENT : "#222222";
                btn.setStyle(StyleFactory.buildTabStyle(hoverBg, hoverTxt));
            });
            btn.setOnMouseExited(e -> StyleFactory.applyTabStyle(btn, idx == activeTab));
            btn.setOnAction(e -> {
                activeTab = idx;
                for (int j = 0; j < tabs.length; j++)
                    StyleFactory.applyTabStyle(tabs[j], j == activeTab);

                // Refresh stats, no full re-render
                refreshStatsPanel(characters.get(activeTab));
            });

            tabs[i] = btn;
        }

        HBox tabBar = new HBox(6, tabs);
        tabBar.setAlignment(Pos.CENTER_LEFT);
        tabBar.setPadding(new Insets(0, 0, 0, 4));
        return tabBar;
    }

    // =============================================
    // MAIN PROFILE CARD
    // =============================================
    private HBox createMainProfileCard() {
        HBox mainCard = new HBox(20);
        mainCard.setStyle("-fx-background-color: " + Constants.CARD_GREY + ";" + ";" + "-fx-border-width: 3;" + "-fx-border-radius: 12;" + "-fx-background-radius: 12;");
        mainCard.setPadding(new Insets(16));
        mainCard.setAlignment(Pos.CENTER);
        statsPanel = createStatsPanel();
        mainCard.getChildren().addAll(createPortraitGrid(), createRelicGrid(), statsPanel);
        return mainCard;
    }

    // =============================================
    // PORTRAIT GRID
    // =============================================
    private GridPane createPortraitGrid() {
        PlayerProfile profile = getCurrentProfile();
        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);

        List<StackPane> avatarNodes = new ArrayList<>();
        for (int i = 0; i < AVATAR_PATHS.length; ++i) {
            final int index = i;
            int col = i % 2;
            int row = i / 2;

            // Inner content: image or fallback circle
            StackPane avatarPane = new StackPane();
            avatarPane.setPrefSize(44, 44);

            Circle clip = new Circle(22);
            clip.setCenterX(22);
            clip.setCenterY(22);

            try {
                ImageView iv = new ImageView(new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(AVATAR_PATHS[i]))));
                iv.setFitWidth(44);
                iv.setFitHeight(44);
                iv.setSmooth(true);
                iv.setClip(clip);
                avatarPane.getChildren().add(iv);
            } catch (Exception e) {
                // No image yet — show a numbered fallback circle
                Circle fallback = new Circle(22, Color.web("#4a4a6a"));
                Label letter = new Label(String.valueOf(i + 1));
                letter.setStyle("-fx-text-fill: white; -fx-font-size: 8px;" +
                        " -fx-font-family: 'Press Start 2P';");
                avatarPane.getChildren().addAll(fallback, letter);
            }

            // Selection ring — yellow if selected, transparent otherwise
            Circle ring = new Circle(24);
            ring.setFill(Color.TRANSPARENT);
            ring.setStrokeWidth(3);
            ring.setStroke(profile.getSelectedAvatarIndex() == i
                    ? Color.web("#FFFF00")
                    : Color.TRANSPARENT);

            StackPane wrapper = new StackPane(avatarPane, ring);
            wrapper.setPrefSize(50, 50);
            wrapper.setCursor(javafx.scene.Cursor.HAND);

            // Click: select this avatar
            wrapper.setOnMouseClicked(e -> {
                profile.setSelectedAvatarIndex(index);
                for (int j = 0; j < avatarNodes.size(); j++) {
                    Circle r = (Circle) avatarNodes.get(j).getChildren().get(1);
                    r.setStroke(j == index
                            ? Color.web("#FFFF00")
                            : Color.TRANSPARENT);
                }
            });

            // Hover glow
            wrapper.setOnMouseEntered(e -> {
                if (profile.getSelectedAvatarIndex() != index)
                    ring.setStroke(Color.web("#888888"));
            });
            wrapper.setOnMouseExited(e -> {
                if (profile.getSelectedAvatarIndex() != index)
                    ring.setStroke(Color.TRANSPARENT);
            });

            avatarNodes.add(wrapper);
            grid.add(wrapper, col, row);
        }

        return grid;
    }

    // =============================================
    // RELIC GRID
    // =============================================
    private GridPane createRelicGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(3);
        grid.setVgap(3);

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                VBox slot = new VBox(2);
                slot.setAlignment(Pos.CENTER);
                slot.setPrefSize(70, 50);

                boolean dark = (row + col) % 2 == 0;
                slot.setStyle("-fx-background-color: " + (dark ? Constants.RELIC_SLOT_DARK : Constants.RELIC_SLOT_LIGHT) + ";");

                Label rName = StyleFactory.createLabel("Long Relic\nName", 6, Constants.WHITE, "-fx-text-alignment: center;");
                Label rVal = StyleFactory.createLabel("1", 6, Constants.WHITE);

                slot.getChildren().addAll(rName, rVal);
                grid.add(slot, col, row);
            }
        }
        return grid;
    }

    // =============================================
    // STATS PANEL
    // =============================================
    private void updateStats(VBox target, Character c) {
        target.getChildren().addAll(
                StyleFactory.createLabel(c.getName(), 14, Constants.WHITE),
                StyleFactory.createLabel(c.getClassType().getDisplayName(), 14, Constants.WHITE),
                StyleFactory.createLabel("Base ATK: " + c.getClassType().getAttack(), 14, Constants.WHITE),
                StyleFactory.createLabel("Base DEF: " + c.getClassType().getDefence(), 14, Constants.WHITE),
                StyleFactory.createLabel("Base HP: " + c.getClassType().getMaxHP(), 14, Constants.WHITE)
        );
    }

    private VBox createStatsPanel() {
        VBox stats = new VBox(14);
        stats.setAlignment(Pos.CENTER_LEFT);
        List<Character> characters = getCharacters();
        updateStats(stats, characters.get(activeTab));

        return stats;
    }

    private void refreshStatsPanel(Character c) {
        statsPanel.getChildren().clear();
        updateStats(statsPanel, c);
    }

    // =============================================
    // FOOTER
    // =============================================
    private HBox createFooter() {
        Button backBtn = StyleFactory.createButton("Return to Main");
        backBtn.setOnAction(e -> stage.getScene().setRoot(new MainMenuScreen(stage, engine).getLayout()));

        Button swapBtn = StyleFactory.createButton("Swap Player Profile");
        swapBtn.setOnAction(e -> {
            activePlayer = (activePlayer == 1) ? 2 : 1;
            renderUI();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox footer = new HBox(backBtn, spacer, swapBtn);
        footer.setPadding(new Insets(12, 0, 0, 0));

        return footer;
    }

    public BorderPane getLayout() {
        return layout;
    }
}
