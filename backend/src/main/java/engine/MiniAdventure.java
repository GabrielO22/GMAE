package engine;

import edu.uci.gmae.group16.profiles.PlayerProfile;
import engine.input.Command;
import engine.state.GameState;

import java.util.UUID;

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