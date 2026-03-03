package item;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {
    // This map holds our item templates
    private static final Map<String, Item> itemRegistry = new HashMap<>();

    // this will run once when the game starts
    public static void initializeRegistry() {
        itemRegistry.put("pot_minor_hp", new Item("pot_minor_hp", "Minor Health Potion", "Restores 50 HP", Item.ItemType.POTION, Item.ItemRarity.COMMON));
        itemRegistry.put("misc_gold_coin", new Item("misc_gold_coin", "Gold Coin", "Currency", Item.ItemType.MISCELLANEOUS, Item.ItemRarity.COMMON));
        // we can register hundreds of items here
    }

    // method for creating any item given its id
    public static Item createItem(String itemId) {
        Item template = itemRegistry.get(itemId);
        if (template == null) {
            throw new IllegalArgumentException("Unknown item ID: " + itemId);
        }

        // Return a copy of the template so players aren't sharing the exact same object reference in memory
        return new Item(
                template.getItemId(),
                template.getItemName(),
                template.getDescription(),
                template.getItemType(),
                template.getitemRarity()
        );
    }
}