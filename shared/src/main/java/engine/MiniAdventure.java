package engine;

import engine.input.Command;
import engine.state.GameState;
import engine.state.PlayerNumber;

import java.util.*;

public interface MiniAdventure {
    UUID getID();
    String getName();

    void handleCommand(PlayerNumber playerNumber, Command command);

    // Turn-based: engine calls this once after both players choose actions
    // Real-time: engine calls this once per tick
    void update();

    GameState getGameState();

    boolean isComplete();
}