package menu;

import adventures.runesofreckoning.RunesOfReckoning;
import adventures.runesofreckoning.RunesOfReckoningGameState;
import characters.Character;
import engine.Engine;
import engine.input.Command;
import engine.state.GameStatus;
import engine.state.PlayerNumber;
import items.Item;
import items.ItemType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import profiles.PlayerProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Battle screen for Runes of Reckoning.
 *
 * Input methods (both work simultaneously):
 *   BUTTONS — click Attack / Defend / Swap / Item / Forfeit
 *   TERMINAL — type commands and press Enter:
 *     attack / a       → attack
 *     defend / d       → defend
 *     swap   / s       → prompts for character number (1/2/3)
 *     item   / i       → prompts for item number
 *     forfeit / f      → forfeit
 *     When prompted for a number, type 1, 2, or 3 and press Enter.
 */
public class BattleScreen {

    // Tracks what the terminal is currently waiting for after a multi-step command
    private enum PendingInput { NONE, SWAP, ITEM }

    private final Engine engine;
    private final RunesOfReckoning adventure;
    private final BorderPane layout;

    // Stats panels
    private VBox p1StatsBox;
    private VBox p2StatsBox;

    // Sprites + HP
    private ImageView p1SpriteView;
    private ImageView p2SpriteView;
    private ProgressBar p1HpBar, p2HpBar;
    private Label p1HpLabel, p2HpLabel;

    // Log
    private Label battleLogLabel;
    private Label turnIndicatorLabel;

    // Terminal input
    private TextField terminalInput;
    private PendingInput pendingInput = PendingInput.NONE;

    // Typewriter animation state
    private boolean isAnimating = false;
    private Timeline activeTimeline = null;

    // Cached ordered lists for number-based selection
    private List<Character> swapCandidates = new ArrayList<>();
    private List<Item> itemCandidates      = new ArrayList<>();

    // Action buttons
    private VBox bottomRegion;

    public BattleScreen(javafx.stage.Stage stage, Engine engine, PlayerProfile p1, PlayerProfile p2) {
        this.engine = engine;
        Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 14);
        this.adventure = new RunesOfReckoning(p1, p2);

        layout = new BorderPane();
        layout.setStyle("-fx-background-color: #1a0a2e;");
        layout.setPadding(new Insets(10));

        buildUI();
        refresh();
    }

    // -----------------------------------------------------------------------
    // BUILD STATIC UI
    // -----------------------------------------------------------------------
    private void buildUI() {
        // TOP: P1 stats | Title + turn | P2 stats
        p1StatsBox = new VBox(5);
        p1StatsBox.setAlignment(Pos.TOP_LEFT);
        p1StatsBox.setPrefWidth(200);

        p2StatsBox = new VBox(5);
        p2StatsBox.setAlignment(Pos.TOP_RIGHT);
        p2StatsBox.setPrefWidth(200);

        Label titleLabel = new Label("Runes of Reckoning");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #FFFF00;" +
                " -fx-font-family: 'Press Start 2P';");

        turnIndicatorLabel = new Label("Player 1's Turn");
        turnIndicatorLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #0B6B80;" +
                " -fx-font-family: 'Press Start 2P';");

        VBox titleBox = new VBox(6, titleLabel, turnIndicatorLabel);
        titleBox.setAlignment(Pos.TOP_CENTER);

        HBox topRow = new HBox(p1StatsBox, titleBox, p2StatsBox);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        topRow.setAlignment(Pos.TOP_CENTER);
        topRow.setPadding(new Insets(0, 0, 10, 0));
        layout.setTop(topRow);

        // CENTER: Sprites + HP bars
        p1SpriteView = buildSpriteView(false);
        p2SpriteView = buildSpriteView(true);
        p1HpBar = buildHpBar(); p2HpBar = buildHpBar();
        p1HpLabel = buildHpLabel(); p2HpLabel = buildHpLabel();

        VBox p1Col = new VBox(6, p1SpriteView, p1HpBar, p1HpLabel);
        p1Col.setAlignment(Pos.CENTER);
        VBox p2Col = new VBox(6, p2SpriteView, p2HpBar, p2HpLabel);
        p2Col.setAlignment(Pos.CENTER);

        Label arenaLabel = new Label("⚔");
        arenaLabel.setStyle("-fx-font-size: 64px; -fx-text-fill: #333355;");

        HBox centerRow = new HBox(40, p1Col, arenaLabel, p2Col);
        centerRow.setAlignment(Pos.CENTER);
        layout.setCenter(centerRow);

        // BOTTOM: log + terminal input + action buttons
        battleLogLabel = new Label("> Battle begins!");
        battleLogLabel.setWrapText(true);
        battleLogLabel.setMaxWidth(740);
        battleLogLabel.setMinHeight(80);
        battleLogLabel.setStyle(
                "-fx-font-size: 10px; -fx-text-fill: #00FF00;" +
                        "-fx-font-family: 'Press Start 2P';" +
                        "-fx-background-color: #000000;" +
                        "-fx-padding: 10;" +
                        "-fx-border-color: #444444; -fx-border-width: 2;");

        // Command hint
        Label hintLabel = new Label("attack  |  defend  |  swap  |  item  |  forfeit");
        hintLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #335533; -fx-font-family: 'Press Start 2P';");

        // Terminal input row: "> ___________"
        Label promptLabel = new Label(">");
        promptLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #00FF00;" +
                " -fx-font-family: 'Press Start 2P';");

        terminalInput = new TextField();
        terminalInput.setPromptText("attack / defend / swap / item / forfeit");
        terminalInput.setStyle(
                "-fx-background-color: #000000;" +
                        "-fx-text-fill: #00FF00;" +
                        "-fx-font-family: 'Press Start 2P';" +
                        "-fx-font-size: 10px;" +
                        "-fx-border-color: #444444;" +
                        "-fx-border-width: 1;" +
                        "-fx-prompt-text-fill: #335533;");
        terminalInput.setPrefWidth(600);
        terminalInput.setOnAction(e -> handleTerminalInput(terminalInput.getText().trim()));

        HBox terminalRow = new HBox(8, promptLabel, terminalInput);
        terminalRow.setAlignment(Pos.CENTER_LEFT);
        terminalRow.setStyle("-fx-background-color: #000000; -fx-padding: 4 10;");

        bottomRegion = new VBox(8);
        bottomRegion.setAlignment(Pos.CENTER);
        bottomRegion.setPadding(new Insets(4, 0, 0, 0));

        VBox fullBottom = new VBox(4, battleLogLabel, hintLabel, terminalRow, bottomRegion);
        fullBottom.setAlignment(Pos.CENTER);
        layout.setBottom(fullBottom);
    }

    // -----------------------------------------------------------------------
    // TERMINAL INPUT PARSER
    // -----------------------------------------------------------------------
    private void handleTerminalInput(String raw) {
        if (raw.isEmpty()) return;
        if (isAnimating) return; // wait for typewriter to finish
        terminalInput.clear();

        String input = raw.toLowerCase().trim();

        // If waiting for a swap number
        if (pendingInput == PendingInput.SWAP) {
            handleSwapSelection(input);
            return;
        }

        // If waiting for an item number
        if (pendingInput == PendingInput.ITEM) {
            handleItemSelection(input);
            return;
        }

        // Normal command parsing
        switch (input) {
            case "attack", "a"  -> processCommand(Command.ACTION);
            case "defend", "d"  -> processCommand(Command.MOVE_DOWN);
            case "swap",   "s"  -> openSwapPicker();
            case "item",   "i"  -> openItemPickerTerminal();
            case "forfeit", "f" -> processCommand(Command.CONFIRM);
            default -> appendLog("Unknown command: '" + raw + "'. Try: attack, defend, swap, item, forfeit");
        }

        Platform.runLater(() -> terminalInput.requestFocus());
    }

    private void handleSwapSelection(String input) {
        try {
            int index = Integer.parseInt(input) - 1; // user types 1-based
            if (index < 0 || index >= swapCandidates.size()) {
                appendLog("Invalid number. Choose 1-" + swapCandidates.size() + ".");
                return;
            }
            Character chosen = swapCandidates.get(index);
            RunesOfReckoningGameState state = currentState();
            List<Character> team = state.playerTurn() == PlayerNumber.PLAYER_ONE
                    ? state.playerOne().getCharacters()
                    : state.playerTwo().getCharacters();

            int targetIndex = team.indexOf(chosen);
            if (targetIndex < 0) {
                appendLog("Character not found.");
                pendingInput = PendingInput.NONE;
                return;
            }

            pendingInput = PendingInput.NONE;
            swapCandidates.clear();
            processSwapToIndex(targetIndex);

        } catch (NumberFormatException e) {
            appendLog("Please enter a number (e.g. 1, 2, 3).");
        }
        Platform.runLater(() -> terminalInput.requestFocus());
    }

    private void handleItemSelection(String input) {
        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= itemCandidates.size()) {
                appendLog("Invalid number. Choose 1-" + itemCandidates.size() + ".");
                return;
            }
            Item chosen = itemCandidates.get(index);
            pendingInput = PendingInput.NONE;
            itemCandidates.clear();
            processItemUse(chosen);

        } catch (NumberFormatException e) {
            appendLog("Please enter a number (e.g. 1, 2, 3).");
        }
        Platform.runLater(() -> terminalInput.requestFocus());
    }

    // -----------------------------------------------------------------------
    // SWAP PICKER — terminal and button versions
    // -----------------------------------------------------------------------
    private void openSwapPicker() {
        RunesOfReckoningGameState state = currentState();
        List<Character> team = state.playerTurn() == PlayerNumber.PLAYER_ONE
                ? state.playerOne().getCharacters()
                : state.playerTwo().getCharacters();
        int currentIndex = state.playerTurn() == PlayerNumber.PLAYER_ONE
                ? state.playerOneActiveIndex()
                : state.playerTwoActiveIndex();

        swapCandidates.clear();
        StringBuilder sb = new StringBuilder("Choose character to swap to:\n");
        int num = 1;
        for (int i = 0; i < team.size(); i++) {
            Character c = team.get(i);
            if (i != currentIndex && c.getCurrentHP() > 0) {
                swapCandidates.add(c);
                sb.append("  ").append(num).append(". ").append(c.getName())
                        .append(" (").append(c.getCurrentHP()).append("/").append(c.getMaxHP()).append(" HP)");
                num++;
            }
        }

        if (swapCandidates.isEmpty()) {
            appendLog("No other characters available to swap!");
            return;
        }

        pendingInput = PendingInput.SWAP;
        appendLog(sb.toString());
    }



    // -----------------------------------------------------------------------
    // ITEM PICKER — terminal and button versions
    // -----------------------------------------------------------------------
    private void openItemPickerTerminal() {
        RunesOfReckoningGameState state = currentState();
        Map<Item, Integer> items = state.activeInventory().getItems();

        itemCandidates.clear();
        if (items.isEmpty()) {
            appendLog(state.activeOne().getName() + " has no items!");
            return;
        }

        StringBuilder sb = new StringBuilder("Choose an item:\n");
        int num = 1;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Item item = entry.getKey();
            int qty = entry.getValue();
            itemCandidates.add(item);
            boolean targetsFoe = item.getType() == ItemType.DEBUFF;
            String countStr = item.hasDurability()
                    ? "[" + item.getCurrentDurability() + "/" + item.getMaxDurability() + " dur]"
                    : "x" + qty;
            String target = targetsFoe ? "[FOE]" : "[SELF]";
            sb.append("  ").append(num).append(". ").append(item.getName())
                    .append(" ").append(countStr).append(" ").append(target);
            if (item.isBroken()) sb.append(" (BROKEN)");
            num++;
        }

        pendingInput = PendingInput.ITEM;
        appendLog(sb.toString());
    }



    // -----------------------------------------------------------------------
    // DIRECT SWAP TO INDEX (bypasses controller's cycle-swap logic)
    // -----------------------------------------------------------------------
    private void processSwapToIndex(int targetIndex) {
        adventure.swapToIndex(currentState().playerTurn(), targetIndex);
        adventure.update();
        refresh();
        Platform.runLater(() -> terminalInput.requestFocus());
    }

    // -----------------------------------------------------------------------
    // STANDARD COMMAND + ITEM USE
    // -----------------------------------------------------------------------
    private void processCommand(Command cmd) {
        adventure.handleCommand(currentState().playerTurn(), cmd);
        adventure.update();
        refresh();
        Platform.runLater(() -> terminalInput.requestFocus());
    }

    private void processItemUse(Item item) {
        adventure.handleItemUsed(currentState().playerTurn(), item);
        adventure.update();
        refresh();
        Platform.runLater(() -> terminalInput.requestFocus());
    }

    // -----------------------------------------------------------------------
    // REFRESH
    // -----------------------------------------------------------------------
    private void refresh() {
        RunesOfReckoningGameState state = currentState();

        refreshStatsBox(p1StatsBox, state.playerOne(), state.playerOneActiveIndex(), true);
        refreshStatsBox(p2StatsBox, state.playerTwo(), state.playerTwoActiveIndex(), false);
        refreshSprite(p1SpriteView, state.activeOne());
        refreshSprite(p2SpriteView, state.activeTwo());
        refreshHpBar(p1HpBar, p1HpLabel, state.activeOne());
        refreshHpBar(p2HpBar, p2HpLabel, state.activeTwo());

        // Clear log then animate new message
        battleLogLabel.setText("");
        appendLog(state.lastActionLog());

        if (state.status() == GameStatus.ENDED) {
            String winnerName = state.winner() == PlayerNumber.PLAYER_ONE
                    ? state.playerOne().getPlayerName()
                    : state.playerTwo().getPlayerName();
            turnIndicatorLabel.setText(winnerName + " WINS!");
            turnIndicatorLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #FFFF00;" +
                    " -fx-font-family: 'Press Start 2P';");
            pendingInput = PendingInput.NONE;
            showReturnButton();
        } else {
            boolean p1Acting = state.playerTurn() == PlayerNumber.PLAYER_ONE;
            turnIndicatorLabel.setText(p1Acting ? "▶ Player 1's Turn" : "▶ Player 2's Turn");
            Platform.runLater(() -> terminalInput.requestFocus());
        }
    }



    private void showReturnButton() {
        bottomRegion.getChildren().clear();
        Button returnBtn = createBtn("Return to Menu", "#555555", "#888888");
        returnBtn.setOnAction(e -> engine.returnToMainMenu());
        bottomRegion.getChildren().add(returnBtn);
    }

    // -----------------------------------------------------------------------
    // STATS BOX
    // -----------------------------------------------------------------------
    private void refreshStatsBox(VBox box, PlayerProfile profile, int activeIndex, boolean isP1) {
        box.getChildren().clear();
        Character active = profile.getCharacters().get(activeIndex);

        box.getChildren().add(styledLabel(active.getName(), "#FFFF00", 11));
        box.getChildren().add(styledLabel(
                active.getClassType() != null ? active.getClassType().getDisplayName() : "?",
                "#0B6B80", 10));
        box.getChildren().add(styledLabel("Attack:  " + active.getAttack(),  "#EEEEEE", 9));
        box.getChildren().add(styledLabel("Defense: " + active.getDefence(), "#EEEEEE", 9));
        String hpColor = active.getCurrentHP() > active.getMaxHP() * 0.5 ? "#44CC44" : "#FF4444";
        box.getChildren().add(styledLabel(
                "Health:  " + active.getCurrentHP() + "/" + active.getMaxHP(), hpColor, 9));

        // Defending flag is unused now (defend = heal, no persistent state)
        // Kept in GameState for potential future use

        box.getChildren().add(styledLabel("── Team ──", "#555555", 8));
        List<Character> team = profile.getCharacters();
        for (int i = 0; i < team.size(); i++) {
            Character c = team.get(i);
            boolean isActive = (i == activeIndex);
            boolean isKO     = c.getCurrentHP() <= 0;
            String color  = isKO ? "#555555" : (isActive ? "#FFFF00" : "#AAAAAA");
            String marker = isActive ? "▶ " : (isKO ? "✗ " : "  ");
            box.getChildren().add(styledLabel(marker + c.getName(), color, 8));
        }
    }

    // -----------------------------------------------------------------------
    // HELPERS
    // -----------------------------------------------------------------------
    /**
     * Appends a message to the log with a typewriter animation (50ms per character).
     * Blocks terminal input while animating so players can't queue commands mid-print.
     */
    private void appendLog(String message) {
        String prefix = battleLogLabel.getText().isEmpty() ? "" : "\n";
        typewriterPrint(prefix + "> " + message);
    }

    /**
     * Animates text onto the log label one character at a time, matching the
     * fancyPrint() style from the original console system.
     * 50ms per character — fast enough to feel snappy, slow enough to read.
     */
    private void typewriterPrint(String fullText) {
        // Cancel any in-progress animation so new text doesn't stack weirdly
        if (activeTimeline != null) {
            activeTimeline.stop();
        }

        String baseText = battleLogLabel.getText();
        isAnimating = true;
        terminalInput.setDisable(true);

        // Build the string one char at a time using a Timeline
        final int[] index = {0};
        activeTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            if (index[0] < fullText.length()) {
                battleLogLabel.setText(baseText + fullText.substring(0, index[0] + 1));
                index[0]++;
            } else {
                // Animation complete
                activeTimeline.stop();
                isAnimating = false;
                terminalInput.setDisable(false);
                Platform.runLater(() -> terminalInput.requestFocus());
            }
        }));

        activeTimeline.setCycleCount(fullText.length() + 1);
        activeTimeline.play();
    }

    private void refreshSprite(ImageView view, Character character) {
        if (character == null || character.getClassType() == null) return;
        try {
            view.setImage(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(character.getClassType().getSpritePath()))));
        } catch (Exception e) {
            System.err.println("Could not load sprite: " + e.getMessage());
        }
    }

    private void refreshHpBar(ProgressBar bar, Label label, Character c) {
        double ratio = c.getMaxHP() > 0 ? (double) c.getCurrentHP() / c.getMaxHP() : 0;
        bar.setProgress(Math.max(0, ratio));
        bar.setStyle(ratio > 0.5  ? "-fx-accent: #44CC44;" :
                ratio > 0.25 ? "-fx-accent: #FFAA00;" : "-fx-accent: #FF4444;");
        label.setText(c.getCurrentHP() + " / " + c.getMaxHP() + " HP");
    }

    private ImageView buildSpriteView(boolean mirrored) {
        ImageView iv = new ImageView();
        iv.setFitWidth(96); iv.setFitHeight(96); iv.setSmooth(false);
        if (mirrored) iv.setScaleX(-1);
        return iv;
    }

    private ProgressBar buildHpBar() {
        ProgressBar bar = new ProgressBar(1.0);
        bar.setPrefWidth(130);
        bar.setStyle("-fx-accent: #44CC44;");
        return bar;
    }

    private Label buildHpLabel() {
        Label l = new Label("-- / -- HP");
        l.setStyle("-fx-font-size: 9px; -fx-text-fill: #EEEEEE; -fx-font-family: 'Press Start 2P';");
        return l;
    }

    private Label styledLabel(String text, String color, int size) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size: " + size + "px; -fx-text-fill: " + color +
                "; -fx-font-family: 'Press Start 2P';");
        return l;
    }

    private Button createBtn(String text, String bg, String hover) {
        Button btn = new Button(text);
        btn.setPrefWidth(130);
        String base = "-fx-background-color: " + bg + "; -fx-border-color: #FFFFFF;" +
                " -fx-border-width: 3; -fx-text-fill: #FFFFFF; -fx-font-size: 10px;" +
                " -fx-padding: 8 10; -fx-font-family: 'Press Start 2P'; -fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(base.replace(bg, hover)));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
        return btn;
    }

    private RunesOfReckoningGameState currentState() {
        return (RunesOfReckoningGameState) adventure.getGameState();
    }

    public BorderPane getLayout() { return layout; }
}