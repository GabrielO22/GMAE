package items;
import characters.Character;

import java.util.UUID;

public class Item {
    public static final int DEFAULT_WEAPON_DURABILITY = 10;

    private String itemID;
    private String name;
    private ItemType type;
    private ItemEffect effect;

    private int maxDurability;
    private int currentDurability;

    public Item(String name, ItemType type, ItemEffect effect) {
        this.itemID = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.effect = effect;
        this.maxDurability = 0;
        this.currentDurability = 0;
    }

    public Item(String name, ItemType type, ItemEffect effect, int durability) {
        this(name, type, effect);
        this.maxDurability = durability;
        this.currentDurability = durability;
    }

    public String getItemID() { return itemID; }
    public String getName()   { return name; }
    public ItemType getType() { return type; }

    public int getMaxDurability()     { return maxDurability; }
    public int getCurrentDurability() { return currentDurability; }

    public boolean hasDurability() { return type == ItemType.WEAPON; }

    public boolean useDurability() {
        if (!hasDurability()) return false;
        currentDurability = Math.max(0, currentDurability - 1);
        return currentDurability == 0;
    }

    public void resetDurability() {
        currentDurability = maxDurability;
    }

    public boolean isBroken() {
        return hasDurability() && currentDurability <= 0;
    }

    public void applyEffect(Character target) {
        effect.apply(target);
    }
}