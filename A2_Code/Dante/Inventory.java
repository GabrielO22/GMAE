import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Inventory {
    UUID inventoryID;
    List<InventoryItem> items;

    Inventory() {
        this.inventoryID = UUID.randomUUID();
        this.items = new ArrayList<>();
    }

    public UUID getInventoryID() {
        return inventoryID;
    }

    public List<InventoryItem> getItems() {
        return items;
    }

    public void addItem(InventoryItem item) {
        this.items.add(item);
    }

    public void removeItem(InventoryItem item) {
        this.items.remove(item);
    }

    public void applyChange(InventoryItem item, int delta) {
        for (InventoryItem i : items) {
            if (i.getName().equals(item.getName())) {
                i.adjustQuantity(delta);
                return;
            }
        }

        this.items.add(new InventoryItem(item.getName(), delta));
    }
}
