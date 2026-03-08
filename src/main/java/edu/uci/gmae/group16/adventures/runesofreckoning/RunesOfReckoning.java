package edu.uci.gmae.group16.adventures.runesofreckoning;

import edu.uci.gmae.group16.engine.MiniAdventure;
import edu.uci.gmae.group16.engine.input.Command;
import edu.uci.gmae.group16.engine.state.GameState;
import edu.uci.gmae.group16.engine.state.GameStatus;
import edu.uci.gmae.group16.engine.state.PlayerNumber;
import edu.uci.gmae.group16.profiles.PlayerProfile;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import edu.uci.gmae.group16.app.Main;

import java.util.UUID;

public class RunesOfReckoning implements MiniAdventure {
    private final UUID id;
    private final String name;

    private RunesOfReckoningGameState state;
    private RunesOfReckoningController controller;

    public RunesOfReckoning(PlayerProfile p1, PlayerProfile p2) {
        id = UUID.randomUUID();
        name = "Runes of Reckoning";
        state = RunesOfReckoningGameState.init(p1, p2);
        controller = new RunesOfReckoningController();
    }

    @Override
    public UUID getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void handleCommand(PlayerNumber playerNumber, Command command) {
        state = controller.handleCommand(playerNumber, command, state);
    }

    @Override
    public void update() {
        state = controller.tick(state);
    }

    @Override
    public GameState getGameState() {
        return state;
    }

    @Override
    public boolean isComplete() {
        return state.status() == GameStatus.ENDED;
    }
}
