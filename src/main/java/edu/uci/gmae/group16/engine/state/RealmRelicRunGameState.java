package edu.uci.gmae.group16.engine.state;

import edu.uci.gmae.group16.adventures.Tile;
import edu.uci.gmae.group16.profiles.*;
import edu.uci.gmae.group16.reuse.Character;
import edu.uci.gmae.group16.reuse.*;

public record RealmRelicRunGameState(
        GameStatus status,
        int currentTick,
        int FPS,
        PlayerProfile playerOne,
        PlayerProfile playerTwo,
        Realm realm,
        Character characterOne,
        Character characterTwo,
        Tile[][] tiles
) implements RealTimeGameState {}
