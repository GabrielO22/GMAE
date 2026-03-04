package GuildQuest.core;

public class Inventory {
    private static int count = 0;

    String inventoryID;
    String name;
    String rarity;
    String type;
    String description;

    public Inventory(String name, String rarity, String type, String description) {
        this.inventoryID = "inventory" + count++;
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.description = description;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void print() {
        String border = "-".repeat(20);
        String name = "\nINVENTORY: " + this.name;
        String ID = "\nID: " + this.inventoryID;
        String rarity = "\nRarity: " + this.rarity;
        String type = "\nType: " + this.type;
        String description = "\nDescription: " + this.description + "\n";
        String str = border + name + ID + rarity + type + description;
        System.out.println(str);
    }

    @Override
    public String toString() {
        return name;
    }
}