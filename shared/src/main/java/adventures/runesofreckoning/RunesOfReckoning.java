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

    /** Called by BattleScreen when the player selects a specific item from the picker. */
    public void handleItemUsed(PlayerNumber playerNumber, items.Item item) {
        state = controller.handleItemUsed(playerNumber, item, state);
    }

    /** Called by BattleScreen when the player picks a specific character to swap to. */
    public void swapToIndex(PlayerNumber playerNumber, int targetIndex) {
        state = controller.swapToIndex(playerNumber, targetIndex, state);
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