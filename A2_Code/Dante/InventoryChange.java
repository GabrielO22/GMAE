import java.util.UUID;

class InventoryChange {
    UUID changeID;
    int quantityDelta;
    InventoryItem item;
    Character targetCharacter;

    InventoryChange(Character targetCharacter, InventoryItem item, int quantityDelta) {
        this.changeID = UUID.randomUUID();
        this.targetCharacter = targetCharacter;
        this.item = item;
        this.quantityDelta = quantityDelta;
    }

    public UUID getChangeID() {
        return changeID;
    }

    public int getQuantityDelta() {
        return quantityDelta;
    }

    public void setQuantityDelta(int quantityDelta) {
        this.quantityDelta = quantityDelta;
    }

    public InventoryItem getItem() {
        return item;
    }

    public void setItem(InventoryItem item) {
        this.item = item;
    }

    public Character getTargetCharacter() {
        return targetCharacter;
    }

    public void setTargetCharacter(Character targetCharacter) {
        this.targetCharacter = targetCharacter;
    }

    public void execute() {
        if (targetCharacter != null && targetCharacter.inventory != null) {
            targetCharacter.inventory.applyChange(item, quantityDelta);
            System.out.println("Executed change: " + (quantityDelta > 0 ? "+" : "")
                    + quantityDelta + " " + item.getName() + " for " + targetCharacter.name);
        }
    }
}
