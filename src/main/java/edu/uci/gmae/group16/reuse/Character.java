package edu.uci.gmae.group16.reuse;

import edu.uci.gmae.group16.characters.CharacterType;

import java.util.*;

public class Character {
    private final UUID id;
    private String name;    // name no longer final for extra user control

    private CharacterType classType;   // Changed classType to its own class to store additional data about what different classes actually mean

    private final Inventory inventory;

    // All below attributes were added for the battle system
    private int maxHP;
    private int currentHP;
    private int attack;
    private int defence;
    private double speed;

    public Character() {    // Character now takes no inputs, as the user gets to decide name and classType after creation
        id = UUID.randomUUID();
        name = "";
        classType = null;
        inventory = new Inventory();

        maxHP = 0;
        currentHP = maxHP;
        attack = 0;
        defence = 0;
        speed = 0;
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
        this.maxHP = classType.getMaxHP();
        this.currentHP = maxHP;
        this.attack = classType.getAttack();
        this.defence = classType.getDefence();
        this.speed = classType.getSpeed();
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
    public double getSpeed() {
        return speed;
    }
    public void modifySpeed(double modification) {
        speed += modification;
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