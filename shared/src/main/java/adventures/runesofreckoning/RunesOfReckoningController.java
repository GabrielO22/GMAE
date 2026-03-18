package adventures.runesofreckoning;

import engine.input.Command;
import engine.state.PlayerNumber;

public class RunesOfReckoningController {
    public RunesOfReckoningController() {

    }

    public RunesOfReckoningGameState handleCommand(PlayerNumber playerNumber, Command command, RunesOfReckoningGameState state) {
        return state;
    }

    public RunesOfReckoningGameState tick(RunesOfReckoningGameState state) {
        return state;
    }
}
