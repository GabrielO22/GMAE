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
                ItemFactory.createHealthPotion(), 30,
                ItemFactory.createSpear(),        20,
                ItemFactory.createHermesBoots(),  20,
                ItemFactory.createSword(),        15,
                ItemFactory.createDebuffScroll(), 15
        );
        RealmRegistry.register(new Realm("FOREST", 25, 25, forestLoot));

        Map<Item, Integer> lavaLoot = Map.of(
                ItemFactory.createAssassinBlade(), 30,
                ItemFactory.createSword(),         25,
                ItemFactory.createCursedRune(),    25,
                ItemFactory.createHealthPotion(),  10,
                ItemFactory.createPoisonVial(),    10
        );
        RealmRegistry.register(new Realm("LAVA", 25, 25, lavaLoot));

        Map<Item, Integer> iceLoot = Map.of(
                ItemFactory.createHermesBoots(),  35,
                ItemFactory.createLeadenBoots(),  25,
                ItemFactory.createSpear(),        20,
                ItemFactory.createHealthPotion(), 10,
                ItemFactory.createShield(),       10
        );
        RealmRegistry.register(new Realm("ICE", 25, 25, iceLoot));

        Map<Item, Integer> desertLoot = Map.of(
                ItemFactory.createHealthPotion(), 35,
                ItemFactory.createArmor(),        25,
                ItemFactory.createHelmet(),       20,
                ItemFactory.createDebuffScroll(), 20
        );
        RealmRegistry.register(new Realm("DESERT", 25, 25, desertLoot));

        Map<Item, Integer> mudLoot = Map.of(
                ItemFactory.createPoisonVial(),   30,
                ItemFactory.createLeadenBoots(),  25,
                ItemFactory.createCursedRune(),   25,
                ItemFactory.createShield(),       10,
                ItemFactory.createHealthPotion(), 10
        );
        RealmRegistry.register(new Realm("MUD", 25, 25, mudLoot));

        Map<Item, Integer> minesLoot = Map.of(
                ItemFactory.createSword(),         30,
                ItemFactory.createAssassinBlade(), 25,
                ItemFactory.createSpear(),         20,
                ItemFactory.createPoisonVial(),    15,
                ItemFactory.createCursedRune(),    10
        );
        RealmRegistry.register(new Realm("MINES", 25, 25, minesLoot));

        MiniAdventureRegistry.register(new RealmRelicRun(playerOne, playerTwo));
        MiniAdventureRegistry.register(new RunesOfReckoning(playerOne, playerTwo));
    }
}