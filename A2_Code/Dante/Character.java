import java.util.UUID;

class Character {
    UUID characterID;
    String name;
    String clazz;
    int level;
    Inventory inventory;

    Character(String name, String clazz) {
        this.characterID = UUID.randomUUID();
        this.name = name;
        this.clazz = clazz;
        this.level = 0;
        this.inventory = new Inventory();
    }

    public UUID getCharacterID() {
        return characterID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
