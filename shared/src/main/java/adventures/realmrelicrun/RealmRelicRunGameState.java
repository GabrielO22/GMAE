package adventures.realmrelicrun;

import engine.RealmRegistry;
import engine.state.GameStatus;
import engine.state.RealTimeGameState;
import profiles.PlayerProfile;
import reuse.Character;
import reuse.Realm;

public record RealmRelicRunGameState(
        GameStatus status,
        int currentTick,
        PlayerProfile playerOne,
        PlayerProfile playerTwo,
        Realm realm,
        Character characterOne,
        Character characterTwo,
        Tile[][] tiles
) implements RealTimeGameState {

    public static RealmRelicRunGameState init(PlayerProfile p1, PlayerProfile p2) {
        Realm realm = RealmRegistry.getRandom();
        return new RealmRelicRunGameState(
                GameStatus.RUNNING,
                0,
                p1,
                p2,
                realm,
                p1.getCharacters().get(0),
                p2.getCharacters().get(0),
                new Tile[realm.getGridHeight()][realm.getGridWidth()]
                );
    }
}
