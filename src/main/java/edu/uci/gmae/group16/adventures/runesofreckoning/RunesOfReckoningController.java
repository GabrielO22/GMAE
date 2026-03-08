package edu.uci.gmae.group16.adventures.runesofreckoning;

import edu.uci.gmae.group16.engine.input.Command;
import edu.uci.gmae.group16.engine.state.PlayerNumber;

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
