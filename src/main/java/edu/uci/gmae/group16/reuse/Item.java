package edu.uci.gmae.group16.reuse;

import edu.uci.gmae.group16.items.ItemEffect;

public class Item {
    private String name;
    private int quantity;
    private ItemType type;
    private ItemEffect effect;

    public Item(String name, int quantity, ItemType type, ItemEffect effect) {
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemType getType() {
        return type;
    }

    public void applyEffect(Character target) {
        effect.apply(target);
    }
}
