package items;

import java.util.HashMap;
import java.util.Map;
public class Inventory {
    private final Map<Item, Integer> items; // Changed from String to Item for efficiency

    public Inventory() {
        items = new HashMap<>();
    }

    public void addItem(Item item, int quantity) { // changed to fit Item not String
        // search by item name
        for (Item existingItem : items.keySet()) {
            // If it has the same name AND it is NOT a weapon with durability, stack it
            if (existingItem.getName().equals(item.getName()) && !existingItem.hasDurability()) {
                items.put(existingItem, items.get(existingItem) + quantity);
                return;
            }
        }

        items.put(item, quantity);
    }

    public void useItem(Item item, int quantity) { // changed to fit Item not String
        // search inventory for item by name
        Item matchedItem = null;
        for (Item existingItem : items.keySet()) {
            if (existingItem.getName().equals(item.getName())) {
                matchedItem = existingItem;
                break;
            }
        }

        // If item found, reduce quantity or remove it
        if (matchedItem != null) {
            int currentQty = items.get(matchedItem);
            if (currentQty > quantity) {
                items.put(matchedItem, currentQty - quantity);
            } else {
                items.remove(matchedItem);
            }
        }
    }

    public Map<Item, Integer> getItems() { // Changed to fit Item not String
        return items;
    }
}

/*
ORIGINAL CLASS - FROM GABRIEL O'HARA A2

import java.util.*;
public class Inventory {
    private final Map<String, Integer> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public void addItem(Item item, int quantity) {
        String name = item.getName();
        if (items.containsKey(name)) {
            items.put(name, items.get(name) + quantity);
        } else {
            items.put(name, quantity);
        }
    }
    public void removeItem(Item item, int quantity) {
        String name = item.getName();
        if (items.containsKey(name) && items.get(name) > quantity)
            items.put(name, items.get(name) - quantity);
        else
            items.remove(name);
    }
    public boolean isEmpty() {
        return items.isEmpty();
    }
    public Map<String, Integer> getItems() {
        return items;
    }
}

 */
