package menu;
import app.Main;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class RunesOfReckoningScreen {
    public void start(Stage stage) {
        Label label = new Label("Runes of Reckoning");
        Button backButton = new Button("Return to Menu");

        backButton.setOnAction(e -> {
            Main main = new Main();
            try {
                main.start(stage);
            } catch (Exception ex) {
                System.out.println("!-- ERROR in TurnDuelAdventure: " + ex.toString() + " --!");
            }
        });

        VBox layout = new VBox(20);

        layout.getChildren().addAll(label, backButton);
        layout.setAlignment(Pos.CENTER);


        Scene scene = new Scene(layout, 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);
    }
}
