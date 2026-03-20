package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LootManager {

    // Helper class to store an item and its drop chance (weight)
    private static class LootEntry {
        String itemName;
        int weight;

        LootEntry(String itemName, int weight) {
            this.itemName = itemName;
            this.weight = weight;
        }
    }

    // Database of all Realm Loot Tables
    private static final Map<String, List<LootEntry>> REALM_LOOT_TABLES = new HashMap<>();

    // Static block to initialize the loot tables
    static {
        // FOREST - Balanced, good starter realm
        List<LootEntry> forestLoot = new ArrayList<>();
        forestLoot.add(new LootEntry("Health Potion", 30));
        forestLoot.add(new LootEntry("Spear", 20));
        forestLoot.add(new LootEntry("Hermes Boots", 20));
        forestLoot.add(new LootEntry("Sword", 15));
        forestLoot.add(new LootEntry("Debuff Scroll", 15));
        REALM_LOOT_TABLES.put("FOREST", forestLoot);

        // LAVA - Aggressive, short and punishing
        List<LootEntry> lavaLoot = new ArrayList<>();
        lavaLoot.add(new LootEntry("Assassin Blade", 30));
        lavaLoot.add(new LootEntry("Sword", 25));
        lavaLoot.add(new LootEntry("Cursed Rune", 25));
        lavaLoot.add(new LootEntry("Health Potion", 10));
        lavaLoot.add(new LootEntry("Poison Vial", 10));
        REALM_LOOT_TABLES.put("LAVA", lavaLoot);

        // ICE - Speed focused, large map
        List<LootEntry> iceLoot = new ArrayList<>();
        iceLoot.add(new LootEntry("Hermes Boots", 35));
        iceLoot.add(new LootEntry("Leaden Boots", 25));
        iceLoot.add(new LootEntry("Spear", 20));
        iceLoot.add(new LootEntry("Health Potion", 10));
        iceLoot.add(new LootEntry("Shield", 10));
        REALM_LOOT_TABLES.put("ICE", iceLoot);

        // DESERT - Survival and utility heavy
        List<LootEntry> desertLoot = new ArrayList<>();
        desertLoot.add(new LootEntry("Health Potion", 35));
        desertLoot.add(new LootEntry("Armor", 25));
        desertLoot.add(new LootEntry("Helmet", 20));
        desertLoot.add(new LootEntry("Debuff Scroll", 20));
        REALM_LOOT_TABLES.put("DESERT", desertLoot);

        // MUD - Debuff heavy, punishing
        List<LootEntry> mudLoot = new ArrayList<>();
        mudLoot.add(new LootEntry("Poison Vial", 30));
        mudLoot.add(new LootEntry("Leaden Boots", 25));
        mudLoot.add(new LootEntry("Cursed Rune", 25));
        mudLoot.add(new LootEntry("Shield", 10));
        mudLoot.add(new LootEntry("Health Potion", 10));
        REALM_LOOT_TABLES.put("MUD", mudLoot);

        // MINES - Weapon heavy, underground
        List<LootEntry> minesLoot = new ArrayList<>();
        minesLoot.add(new LootEntry("Sword", 30));
        minesLoot.add(new LootEntry("Assassin Blade", 25));
        minesLoot.add(new LootEntry("Spear", 20));
        minesLoot.add(new LootEntry("Poison Vial", 15));
        minesLoot.add(new LootEntry("Cursed Rune", 10));
        REALM_LOOT_TABLES.put("MINES", minesLoot);
    }


    // Returns the specific Trophy Relic name for the given map.
    public static String getRealmRelic(String realmName) {
        return switch (realmName.toUpperCase()) {
            case "FOREST" -> "Ancient Seed";
            case "LAVA" -> "Obsidian Skull";
            case "ICE" -> "Frozen Tear";
            case "DESERT" -> "Golden Scarab";
            case "MUD" -> "Dinosaur Egg";
            default -> "Mystery Relic"; // Used for MINES or fallback
        };
    }

     // Rolls a single random item from the specific realm's loot table based on its % chance.
    private static String rollItemForRealm(String realmName, Random rand) {
        List<LootEntry> table = REALM_LOOT_TABLES.getOrDefault(realmName.toUpperCase(), REALM_LOOT_TABLES.get("FOREST"));

        // Generate a number between 1 and 100
        int roll = rand.nextInt(100) + 1;
        int cumulativeWeight = 0;

        for (LootEntry entry : table) {
            cumulativeWeight += entry.weight;
            if (roll <= cumulativeWeight) {
                return entry.itemName;
            }
        }
        return "Health Potion"; // Failsafe
    }

    /**
     * Generates a dynamic loot table for the map, including the Trophy.
     * @param realmName The current map name
     * @param totalCommonItems How many standard items to spawn
     * @return A list of item names ready to be spawned
     */
    public static List<String> generateLootForMap(String realmName, int totalCommonItems) {
        List<String> mapLoot = new ArrayList<>();
        Random rand = new Random();

        // Add the guaranteed Realm Trophy
        mapLoot.add(getRealmRelic(realmName));

        // Roll random items from the Realm's specific weighted table
        for (int i = 0; i < totalCommonItems; i++) {
            mapLoot.add(rollItemForRealm(realmName, rand));
        }

        return mapLoot;
    }
}