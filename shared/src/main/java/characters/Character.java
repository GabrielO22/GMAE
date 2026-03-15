package characters;

import items.Buff;
import items.Inventory;

import java.util.*;

public class Character {
    private final UUID id;
    private String name;    // name no longer final for extra user control

    private CharacterType classType;   // Changed classType to its own class to store additional data about what different classes actually mean

    private final Inventory inventory;
    private final List<Buff> activeBuffs;
    private final Map<String, Object> attributes;

    // All below attributes were added for the battle system
    private int maxHP;
    private int currentHP;
    private int attack;
    private int defence;
    private int speed;
    private double baseCritRate;

    public Character() {    // Character now takes no inputs, as the user gets to decide name and classType after creation
        id = UUID.randomUUID();
        name = "";
        classType = null;
        inventory = new Inventory();
        activeBuffs = new ArrayList<>();
        attributes = new HashMap<>();

        // below is for battle system
        maxHP = 0;
        currentHP = maxHP;
        attack = 0;
        defence = 0;
        speed = 0;
        baseCritRate = 0.05;
    }
    public UUID getID() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) { // Allows user to set character name
        this.name = name;
    }
    public CharacterType getClassType() { // Changed to return new type CharacterClass
        return classType;
    }

    public void setClassType(CharacterType classType) { // Added setter to classtype to allow user to set type. Attributes update once classType is set
        this.classType = classType;
        if (classType != null) {
            this.maxHP = classType.getMaxHP();
            this.currentHP = this.maxHP; // Start at full health
            this.attack = classType.getAttack();
            this.defence = classType.getDefence();
            this.speed = classType.getSpeed();
        }
    }
    public Inventory getInventory() {
        return inventory;
    }

    // All below methods are added for the battle system
    public int getMaxHP() {
        return maxHP;
    }
    public void modifyMaxHP(int modification) {
        maxHP += modification;
    }
    public int getCurrentHP() {
        return currentHP;
    }
    public void modifyCurrentHP(int modification){
        currentHP += modification;
    }
    public int takeDamage(int damage) {
        currentHP = Math.max(0, Math.min(currentHP - damage + defence, currentHP));
        return -1 * Math.min(defence - damage, 0); // returns damage actually dealt for display use
    }
    public int getAttack() {
        return attack;
    }
    public void modifyAttack(int modification) {
        attack += modification;
    }
    public int getDefence() {
        return defence;
    }
    public void modifyDefence(int modification) {
        defence += modification;
    }
    public int getSpeed() {
        return speed;
    }
    public void modifySpeed(int amount) {
        this.speed += amount;
        // Prevent speed from dropping below 1 so they don't get permanently stuck
        if (this.speed < 1) {
            this.speed = 1;
        }
    }
    public double getCritRate() {
        return baseCritRate;
    }
    public void modifyCritRate(double modification) {
        baseCritRate += modification;
    }

    public void addBuff(Buff buff) {
        // Check for duplicates
        for (Buff active : activeBuffs) {
            if (active.getName().equals(buff.getName())) { // DUPLICATE FOUND
                // Don't apply the stats again. Just refresh the duration
                System.out.println(this.name + " already has " + buff.getName() + "! Refreshing duration.");

                // Set the duration to whichever is higher (in case they pick up a stronger version)
                active.setDuration(Math.max(active.getDuration(), buff.getDuration()));

                return; // STOP EXECUTION HERE. Do not add to list again.
            }
        }

        // If it is NOT a duplicate, apply it normally!
        buff.apply(this);
        activeBuffs.add(buff);
    }

    public void updateBuffs() {
        List<Buff> expiredBuffs = new ArrayList<>();
        for (Buff buff : activeBuffs) {
            buff.update();
            if (buff.getDuration() <= 0) {
                expiredBuffs.add(buff);
            }
        }

        for (Buff buff : expiredBuffs) {
            buff.remove(this);
            activeBuffs.remove(buff);
        }
    }

    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public boolean hasAttribute(String key) {
        return this.attributes.containsKey(key);
    }

    public void removeAttribute(String key) {
        this.attributes.remove(key);
    }
}

/*

ORIGINAL CLASS - FROM GABRIEL O'HARA A2
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

 */