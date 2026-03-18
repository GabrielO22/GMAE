package adventures.realmrelicrun;

import engine.input.Command;
import engine.state.PlayerNumber;

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
