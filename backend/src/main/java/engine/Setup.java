package engine;

import adventures.realmrelicrun.RealmRelicRun;
import adventures.runesofreckoning.RunesOfReckoning;
import items.ItemFactory;
import profiles.PlayerProfile;
import items.Item;
import realms.Realm;

import java.util.Map;

public class Setup {
    public static void init() {
        PlayerProfile playerOne = new PlayerProfile();
        PlayerProfile playerTwo = new PlayerProfile();

        Map<Item, Integer> forestLoot = Map.of(
                ItemFactory.createHealthPotion(), 50,
                ItemFactory.createSpear(), 20
        );

        RealmRegistry.register(new Realm("FOREST", 20, 20, forestLoot));
        RealmRegistry.register(new Realm("LAVA", 20, 20, forestLoot));

        MiniAdventureRegistry.register(new RealmRelicRun(playerOne, playerTwo));
        MiniAdventureRegistry.register(new RunesOfReckoning(playerOne, playerTwo));
    }


}
