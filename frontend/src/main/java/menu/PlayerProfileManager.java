package menu;

import engine.Engine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PlayerProfileManager {
    private final Stage stage;
    private final Engine engine;
    private final BorderPane layout;

    private int activeTab = 1;
    private Button[] tabs;

    public PlayerProfileManager(Stage stage, Engine engine) {
        this.stage = stage;
        this.engine = engine;

        layout = new BorderPane();
        layout.setStyle(StyleFactory.createBackground(UIConstants.BG_MAIN));
        layout.setPadding(new Insets(20));
        layout.setPrefSize(768, 576);

        renderUI();
    }

    private void renderUI() {
        layout.setTop(createHeader());
        layout.setCenter(createCenterSection());
        layout.setBottom(createFooter());
    }

    // =============================================
    // HEADER
    // =============================================
    private VBox createHeader() {
        Label title = StyleFactory.createLabel("Player Profile Manager", 20, UIConstants.OFF_WHITE);
        Label name = StyleFactory.createLabel("<Player Name>", 16, UIConstants.WHITE);
        Label relics = StyleFactory.createLabel("Relics Collected: 0", 14, UIConstants.WHITE);
        Label records = StyleFactory.createLabel("Duel Record: 0-0", 14, UIConstants.WHITE);

        VBox header = new VBox(12, title, name, relics, records);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 8, 0));
        return header;
    }

    // =============================================
    // CENTER SECTION
    // =============================================
    private VBox createCenterSection() {
        HBox tabBar = createCharacterTabs();
        HBox mainCard = createMainProfileCard();

        VBox center = new VBox(0, tabBar, mainCard);
        center.setAlignment(Pos.CENTER_LEFT);
        return center;
    }

    // =============================================
    // CHARACTER TABS
    // =============================================
    private HBox createCharacterTabs() {
        String[] tabNames = {"Character A", "Character B", "Character C"};
        tabs = new Button[tabNames.length];

        for (int i = 0; i < tabNames.length; i++) {
            final int idx = i;
            Button btn = new Button(tabNames[i]);
            StyleFactory.applyTabStyle(btn, i == activeTab);

            btn.setOnMouseEntered(e -> {
                boolean sel = (idx == activeTab);
                String hoverBg = sel ? "#555555" : "#BBBBBB";
                String hoverTxt = sel ? UIConstants.RETRO_YELLOW : "#222222";
                btn.setStyle(StyleFactory.buildTabStyle(hoverBg, hoverTxt));
            });
            btn.setOnMouseExited(e -> StyleFactory.applyTabStyle(btn, idx == activeTab));
            btn.setOnAction(e -> {
                activeTab = idx;
                for (int j = 0; j < tabs.length; j++)
                    StyleFactory.applyTabStyle(tabs[j], j == activeTab);

                // TODO: load character data from engine here
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
        mainCard.setStyle("-fx-background-color: " + UIConstants.CARD_GREY + ";" + "-fx-border-color: " + UIConstants.BLUE_BORDER + ";" + "-fx-border-width: 3;" + "-fx-border-radius: 12;" + "-fx-background-radius: 12;");
        mainCard.setPadding(new Insets(16));
        mainCard.setAlignment(Pos.CENTER);

        mainCard.getChildren().addAll(createPortraitGrid(), createRelicGrid(), createStatsPanel());
        return mainCard;
    }

    // =============================================
    // PORTRAIT GRID
    // =============================================
    private GridPane createPortraitGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        for (int row = 0; row < 5; row++)
            for (int col = 0; col < 2; col++)
                grid.add(new Circle(22, Color.BLACK), col, row);

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
            for (int col = 0; col < 6; col++) {
                VBox slot = new VBox(2);
                slot.setAlignment(Pos.CENTER);
                slot.setPrefSize(70, 50);

                boolean dark = (row + col) % 2 == 0;
                slot.setStyle("-fx-background-color: " + (dark ? UIConstants.RELIC_SLOT_DARK : UIConstants.RELIC_SLOT_LIGHT) + ";");

                Label rName = StyleFactory.createLabel("Long Relic\nName", 6, UIConstants.WHITE, "-fx-text-alignment: center;");
                Label rVal = StyleFactory.createLabel("1", 6, UIConstants.WHITE);

                slot.getChildren().addAll(rName, rVal);
                grid.add(slot, col, row);
            }
        }
        return grid;
    }

    // =============================================
    // STATS PANEL
    // =============================================
    private VBox createStatsPanel() {
        VBox stats = new VBox(14);
        stats.setAlignment(Pos.CENTER_LEFT);

        String[] entries = {"<Character Name>", "<Character Class>", "Base Attack: 0", "Base Defense: 0", "Base Health: 0"};
        for (String entry : entries)
            stats.getChildren().add(StyleFactory.createLabel(entry, 11, UIConstants.WHITE));

        return stats;
    }

    // =============================================
    // FOOTER
    // =============================================
    private HBox createFooter() {
        Button backBtn = StyleFactory.createButton("Return to Main");
        backBtn.setOnAction(e -> stage.getScene().setRoot(new MainMenuScreen(stage, engine).getLayout()));

        Button swapBtn = StyleFactory.createButton("Swap Player Profile");

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
