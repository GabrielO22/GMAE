import java.util.Map;
import java.util.Objects;

public class Character {
    private final String characterId;
    private final String characterName;
    private String characterClass;
    private int level;
    private final String ownerId;
    private final Inventory inventory;

    public Character(String characterId, String characterName, String characterClass, String owner) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.characterClass = characterClass;
        this.level = 1; // Start at level 1
        this.ownerId = owner;
        this.inventory = new Inventory("INV-" + characterId, owner);

    }

    // quest reward logic
    public void grantLoot(Map<Item, Integer> loot) {
        loot.forEach(inventory::addItem);
    }

    public void removeLoot(Map<Item, Integer> loot) {
        loot.forEach(inventory::removeItem);
    }

    // furture: modify for xp based/ dynamic leveling?
    public void levelUp() {
        this.level++;
    }

    void changeClass(String characterClass) {
        this.characterClass = characterClass;
    }

    // Getters
    public String getCharacterId() { return characterId; }
    public String getName() { return characterName; }
    public Inventory getInventory() { return inventory; }
    public int getLevel() { return level; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(characterId, character.characterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterId);
    }
}