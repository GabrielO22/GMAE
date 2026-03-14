package items;
import characters.Character;

import java.util.UUID;

public class Item {
    private String itemID;
    private String name;
    private ItemType type;
    private ItemEffect effect;

    public Item(String name, ItemType type, ItemEffect effect) {
        this.itemID = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.effect = effect;
    }

    public String getItemID() {
        return itemID;
    }
    public String getName() { return name; }
    public ItemType getType() {
        return type;
    }

    public void applyEffect(Character target) {
        effect.apply(target);
    }
}
