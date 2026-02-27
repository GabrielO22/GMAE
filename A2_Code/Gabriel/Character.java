import java.util.*;
public class Character {
    private final UUID id;
    private final String name;
    private final String classType;
    private final Inventory inventory;

    public Character(String name, String classType) {
        id = UUID.randomUUID();
        this.name = name;
        this.classType = classType;
        this.inventory = new Inventory();
    }

    public UUID getID() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getClassType() {
        return classType;
    }
    public Inventory getInventory() {
        return inventory;
    }

}
