package menu;

import engine.Engine;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuScreen {
    private Stage stage;
    private Engine engine;
    private VBox layout;

    public MainMenuScreen(Stage stage, Engine engine) {
        this.stage = stage;
        this.engine = engine;
        createLayout();
    }

    private void createLayout() {
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(800, 600);

        Label title = new Label("GuildQuest Mini-Adventure Environment");
        title.setStyle("-fx-font-size: 24px;");

        Button itemCollectionButton = new Button("Realm Relic Run");
        Button turnDuelButton = new Button("Runes of Reckoning");

        itemCollectionButton.setPrefWidth(200);
        turnDuelButton.setPrefWidth(200);

        // When clicked, it tells Engine to launch the correct Swing GamePanel bridge
        itemCollectionButton.setOnAction(e -> {
            AdventureSetupScreen relicScreen = new AdventureSetupScreen(
                    stage, engine, "Realm Relic Run Setup", "Start Game!", "RANDOM"
            );
            stage.getScene().setRoot(relicScreen.getLayout());
        });

        turnDuelButton.setOnAction(e -> {
            AdventureSetupScreen runesScreen = new AdventureSetupScreen(
                    stage, engine, "Runes of Reckoning Setup", "Start Duel!", "LAVA"
            );
            stage.getScene().setRoot(runesScreen.getLayout());
        });

        layout.getChildren().addAll(title, itemCollectionButton, turnDuelButton);
    }

    public VBox getLayout() {
        return layout;
    }
}
