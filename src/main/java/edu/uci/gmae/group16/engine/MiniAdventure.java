package edu.uci.gmae.group16.engine;

import edu.uci.gmae.group16.engine.input.Command;
import edu.uci.gmae.group16.engine.state.GameState;
import edu.uci.gmae.group16.engine.state.PlayerNumber;
import edu.uci.gmae.group16.profiles.PlayerProfile;

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