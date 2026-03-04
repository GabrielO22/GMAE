package GuildQuest.core;

import java.util.List;
import java.util.ArrayList;

public class Character {
    private static int count = 0;

    String characterID;
    String name;
    String classType;
    int level;
    List<Inventory> inventoryList;

    public Character(String name, String classType, int level) {
        this.characterID = "character" + count++;
        this.name = name;
        this.classType = classType;
        this.level = level;
        inventoryList = new ArrayList<>();
    }

    public void addInventory(Inventory i) {
        inventoryList.add(i);
    }

    public void removeInventory(Inventory i) {
        inventoryList.remove(i);
    }

    public void updateInventory(Inventory i, String newName) {
        i.changeName(newName);
    }

    public List<Inventory> getInventory() {
        return List.copyOf(inventoryList);
    }

    public void print() {
        String border = "-".repeat(20);
        String name = "\nCHARACTER: " + this.name;
        String ID = "\nID: " + this.characterID;
        String classType = "\nClass Type: " + this.classType;
        String level = "\nLevel: " + this.level + "\n";
        String str = border + name + ID + classType + level;
        System.out.println(str);
    }

    @Override
    public String toString() {
        return name;
    }
}
