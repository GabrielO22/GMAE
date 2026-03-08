package edu.uci.gmae.group16.adventures.realmrelicrun;

import edu.uci.gmae.group16.engine.input.Command;
import edu.uci.gmae.group16.engine.state.PlayerNumber;

public class RealmRelicRunController {
    public RealmRelicRunController() {

    }

    public RealmRelicRunGameState handleCommand(PlayerNumber playerNumber, Command command, RealmRelicRunGameState state) {
        return state;
    }

    public RealmRelicRunGameState tick(RealmRelicRunGameState state) {
        return state;
    }
}
