package edu.uci.gmae.group16.engine;

import edu.uci.gmae.group16.engine.input.Command;
import edu.uci.gmae.group16.engine.state.GameState;
import edu.uci.gmae.group16.profiles.PlayerProfile;

import java.util.*;

public interface MiniAdventure {
    UUID id();
    String name();

    void initialize(PlayerProfile p1, PlayerProfile p2);
    void handleCommand(int playerIndex, Command command);

    // Turn-based: engine calls this once after both players choose actions
    // Real-time: engine calls this once per tick
    void update();

    GameState getGameState();

    boolean isComplete();
    String getResultSummary();

    void reset();
}