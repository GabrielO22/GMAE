package item;

import java.util.Objects;

public class Item {
    public final String itemId;
    public String itemName;
    public String description;

    public enum ItemType {WEAPON, ARMOR, POTION, QUEST_ITEM, MISCELLANEOUS}
    public enum ItemRarity {UNCOMMON, COMMON, RARE, EPIC, LEGENDARY}

    public ItemType itemType;
    public ItemRarity itemRarity;


    public Item(String itemId, String itemName, String description, ItemType itemType, ItemRarity itemRarity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.itemType = itemType;
        this.itemRarity = itemRarity;

    }

    // Getters
    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public String getDescription() { return description; }
    public ItemType getItemType() { return itemType; }
    public ItemRarity getitemRarity() { return itemRarity; }


    public void updateDetails(ItemType itemType, ItemRarity itemRarity) {
        this.itemType = itemType;
        this.itemRarity = itemRarity;
    }

    public void updateName(String newName) {
        this.itemName = newName;
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }

    @Override
    public String toString() { return itemName + " (" + itemRarity + ")"; }

    // provide support for stacking items in inventory, similar pattern to campaign and character
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemId, item.itemId); // Compare by ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId); // Hash by ID
    }
}