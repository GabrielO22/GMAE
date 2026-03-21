package engine;

import characters.Character;
import characters.CharacterRegistry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import menu.CharacterSelectScreen;
import menu.MainMenuScreen;
import menu.TitleScreen;
import profiles.PlayerProfile;

import javax.swing.SwingUtilities;

public class Engine extends Application {
    private BorderPane mainLayout;
    private Stage window;
    private MainMenuScreen mainMenu;

    private PlayerProfile player1Profile;
    private PlayerProfile player2Profile;

    public PlayerProfile getPlayer1Profile() { return player1Profile; }
    public PlayerProfile getPlayer2Profile() { return player2Profile; }

    public static void main(String[] args) {
        launch(args); // This launches the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        this.window = primaryStage;
        primaryStage.setTitle("GuildQuest Mini-Adventure Environment");

        // Initialize backend logic
        CharacterRegistry.register();
        Setup.init();
        mainLayout = new BorderPane();

        // Pass 'this' (the Engine) to the menu
        // Instead of main menu we now load title screen
        TitleScreen titleScreen = new TitleScreen(this);
        mainLayout.setCenter(titleScreen.getLayout());

        Scene scene = new Scene(mainLayout, 768, 576); // screen settings from game panel
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Closing application...");
            System.exit(0); // This forcefully terminates the JVM
        });

        primaryStage.show();
    }

    public void launchMiniAdventure(String realmName, Character p1Char, Character p2Char) {
        // Create the JavaFX SwingNode
        System.out.println("Engine received request to load map: " + realmName);
        SwingNode swingNode = new SwingNode();

        // Create the Swing GamePanel on the Swing Thread
        SwingUtilities.invokeLater(() -> {
            try {
                GamePanel gamePanel = new GamePanel(this, realmName, p1Char, p2Char);
                swingNode.setContent(gamePanel); // Put the game panel inside the portal
                gamePanel.setupGame(); // set objects on map
                gamePanel.startGameThread(); // Start the tutorial's game loop

                Platform.runLater(() -> {
                    // Put main layout back on screen
                    window.getScene().setRoot(mainLayout);
                    mainLayout.setCenter(swingNode);

                    window.sizeToScene();
                    window.centerOnScreen(); // Recenter the window using saved window
                });
            } catch (Exception ex) { // <-- ADD THIS CATCH BLOCK
                System.out.println("CRASH LOADING MAP: " + realmName);
                ex.printStackTrace();
            }
        });

        // Give the game panel focus so WASD/arrow keys work
        swingNode.requestFocus();
    }

    public void launchMainMenu(PlayerProfile p1, PlayerProfile p2) {
        this.player1Profile = p1;
        this.player2Profile = p2;

        // Pass the Engine and Profiles to the Main Menu
        mainMenu = new MainMenuScreen(window, this);
        returnToMainMenu();
    }

    // Restore the main layout to the window
    public void returnToMainMenu() {
        Platform.runLater(() -> {
            mainLayout.setCenter(mainMenu.getLayout());
            window.sizeToScene();
            window.centerOnScreen();
            mainLayout.requestFocus();
        });
    }

    public void launchDraftScreen() {
        CharacterSelectScreen draftScreen = new CharacterSelectScreen(this);
        mainLayout.setCenter(draftScreen.getLayout());
    }

    public void launchDuel() {
        Platform.runLater(() -> {
            menu.BattleScreen battleScreen = new menu.BattleScreen(
                    window, this, player1Profile, player2Profile);
            mainLayout.setCenter(battleScreen.getLayout());
            window.getScene().setRoot(mainLayout); // restore mainLayout as scene root
            window.sizeToScene();
            window.centerOnScreen();
        });
    }
}