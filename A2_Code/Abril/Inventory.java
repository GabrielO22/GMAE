import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class Inventory {
    private final String inventoryId;
    private final String ownerId;
    private final Map<Item, Integer> items;

    public Inventory(String inventoryId, String owner) {
        this.inventoryId = inventoryId;
        this.ownerId = owner;
        this.items = new HashMap<>();
    }

    public void addItem(Item item, Integer quantity) {
        items.merge(item, quantity, Integer::sum);
    }

    public void removeItem(Item item, Integer quantity) {
        items.computeIfPresent(item, (k, currentQuantity) ->
                currentQuantity <= quantity ? null : currentQuantity - quantity
        );
    }

    public Item findItemByName(String itemName) {
        return items.keySet().stream()
                .filter(item -> item.getItemName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    public void listAllItems() {
        items.forEach((item, qty) -> System.out.println(item.getItemName() + ": " + qty));
    }

    public int countTotalItems() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void clear() {
        items.clear();
    }

    public Map<Item, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

}