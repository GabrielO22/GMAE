package edu.uci.gmae.group16.adventures.realmrelicrun;

import edu.uci.gmae.group16.adventures.realmrelicrun.Tile;
import edu.uci.gmae.group16.engine.RealmRegistry;
import edu.uci.gmae.group16.engine.state.GameStatus;
import edu.uci.gmae.group16.engine.state.RealTimeGameState;
import edu.uci.gmae.group16.profiles.*;
import edu.uci.gmae.group16.reuse.Character;
import edu.uci.gmae.group16.reuse.*;

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
