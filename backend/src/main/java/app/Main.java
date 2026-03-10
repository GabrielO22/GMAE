package app;

import adventures.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Button itemCollectionButton = new Button("Realm Relic Run");
        Button turnDuelButton = new Button("Runes of Reckoning");

        itemCollectionButton.setPrefWidth(200);
        turnDuelButton.setPrefWidth(200);

        itemCollectionButton.setOnAction(e -> {
            ItemCollectionAdventure game = new ItemCollectionAdventure();
            game.start(stage);
        });

        turnDuelButton.setOnAction(e -> {
            TurnDuelAdventure game = new TurnDuelAdventure();
            game.start(stage);
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(itemCollectionButton, turnDuelButton);
        layout.setAlignment(Pos.CENTER);

        Label title = new Label("GuildQuest Mini-Adventure Environment");
        title.setStyle("-fx-font-size: 24px;");
        layout.getChildren().add(0, title);


        Scene scene = new Scene(layout, 800, 600);
        layout.setPrefSize(800, 600);

        stage.setTitle("GuildQuest Mini-Adventure Environment");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

