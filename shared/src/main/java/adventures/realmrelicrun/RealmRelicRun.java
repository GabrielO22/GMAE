package adventures.realmrelicrun;

import engine.MiniAdventure;
import engine.input.Command;
import engine.state.GameState;
import engine.state.GameStatus;
import engine.state.PlayerNumber;
import profiles.PlayerProfile;

import java.util.UUID;

public class RealmRelicRun implements MiniAdventure {
    private final UUID id;
    private final String name;

    private final RealmRelicRunController controller;
    private RealmRelicRunGameState state;

    public RealmRelicRun(PlayerProfile p1, PlayerProfile p2) {
        this.id = UUID.randomUUID();
        this.name = "Realm Relic Run";
        controller = new RealmRelicRunController();
        state = RealmRelicRunGameState.init(p1, p2);
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
