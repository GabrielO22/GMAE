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

    public PlayerProfileManager(Stage stage, Engine engine) {
        this.stage = stage;
        this.engine = engine;

        layout = new BorderPane();
        layout.setStyle(StyleFactory.createBackground(UIConstants.BG_MAIN));
        layout.setPadding(new Insets(20));

        renderUI();
    }

    private void renderUI(){
        layout.setTop(createHeader());
        layout.setCenter(createMainProfileCard());
        layout.setBottom(createFooter());
    }

    private VBox createHeader() {
        Label title = StyleFactory.createLabel("Player Profile Manager",
                28, UIConstants.OFF_WHITE);
        Label name = StyleFactory.createLabel("Active Player",
                20, UIConstants.WHITE);
        Label relics = StyleFactory.createLabel("Relics Collected: 0",
                16, UIConstants.WHITE);
        Label records = StyleFactory.createLabel("Duel Record: 0-0",
                16, UIConstants.WHITE);

        VBox header = new VBox(15, title, name, relics, records);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 20, 0));
        return header;
    }

    private HBox createMainProfileCard() {
        HBox mainCard = new HBox(20);
        mainCard.setStyle(StyleFactory.createBackground(UIConstants.CARD_GREY));
        mainCard.setPadding(new Insets(20));
        mainCard.setAlignment(Pos.CENTER);

        // Character Avatars Grid (2x5 circles)
        GridPane portraitGrid = new GridPane();
        portraitGrid.setHgap(10);
        portraitGrid.setVgap(10);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 2; col++) {
                portraitGrid.add(new Circle(25, Color.BLACK), col, row);
            }
        }

        // Relic Inventory Grid (6x4 blocks)
        GridPane relicGrid = new GridPane();
        relicGrid.setHgap(5);
        relicGrid.setVgap(5);
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 6; col++) {
                VBox relicBox = new VBox(2);
                relicBox.setAlignment(Pos.CENTER);
                relicBox.setPrefSize(70, 50);
                relicBox.setStyle(StyleFactory.createBackground(UIConstants.RELIC_SLOT));

                Label rName = StyleFactory.createLabel("Long Relic\nName",
                        8, UIConstants.WHITE, "-fx-text-alignment: center;");
                Label rVal = StyleFactory.createLabel("1",
                        8, UIConstants.WHITE);

                relicBox.getChildren().addAll(rName, rVal);
                relicGrid.add(relicBox, col, row);
            }
        }

        // 3. Right Side: Stats labels
        VBox stats = new VBox(20);
        stats.setAlignment(Pos.CENTER_LEFT);
        String statStyle = StyleFactory.createTextStyle(14, UIConstants.WHITE);

        stats.getChildren().addAll(
                new Label("<Character Name>"),
                new Label("<Character Class>"),
                new Label("Base Attack: 0"),
                new Label("Base Defense: 0"),
                new Label("Base Health: 0")
        );
        stats.getChildren().forEach(n -> n.setStyle(statStyle));

        mainCard.getChildren().addAll(portraitGrid, relicGrid, stats);
        return mainCard;
    }

    private HBox createFooter() {
        Button backBtn = StyleFactory.createButton("Return to Main");
        //backBtn.setStyle(BUTTON_STYLE + RETRO_FONT + "-fx-font-size: 14px; -fx-padding: 10 20;");
        backBtn.setOnAction(e -> stage.getScene().setRoot(new MainMenuScreen(stage, engine).getLayout()));

        Button swapBtn = StyleFactory.createButton("Swap Player Profile");
        //swapBtn.setStyle(BUTTON_STYLE + RETRO_FONT + "-fx-font-size: 14px; -fx-padding: 10 20;");

        HBox footer = new HBox(backBtn, new Region(), swapBtn);
        HBox.setHgrow(footer.getChildren().get(1), Priority.ALWAYS);
        footer.setPadding(new Insets(20, 0, 0, 0));
        return footer;
    }

    public BorderPane getLayout() {
        return layout;
    }
}
