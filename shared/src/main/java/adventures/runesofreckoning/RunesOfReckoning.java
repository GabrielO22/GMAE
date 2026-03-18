package adventures.runesofreckoning;

import engine.MiniAdventure;
import engine.input.Command;
import engine.state.GameState;
import engine.state.GameStatus;
import engine.state.PlayerNumber;
import profiles.PlayerProfile;

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
