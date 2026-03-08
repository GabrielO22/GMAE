package edu.uci.gmae.group16.engine;

import edu.uci.gmae.group16.adventures.realmrelicrun.RealmRelicRun;
import edu.uci.gmae.group16.adventures.runesofreckoning.RunesOfReckoning;
import edu.uci.gmae.group16.characters.CharacterRegistry;
import edu.uci.gmae.group16.profiles.PlayerProfile;

public class Setup {
    public static void init() {
        PlayerProfile playerOne = new PlayerProfile();
        PlayerProfile playerTwo = new PlayerProfile();

        MiniAdventureRegistry.register(new RealmRelicRun(playerOne, playerTwo));
        MiniAdventureRegistry.register(new RunesOfReckoning(playerOne, playerTwo));
    }


}
