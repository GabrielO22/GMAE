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
