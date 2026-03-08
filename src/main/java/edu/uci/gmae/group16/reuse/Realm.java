package edu.uci.gmae.group16.reuse;

import java.util.*;

public class Realm {
    private final String name;
    private final UUID id;

    // Old attributes removed because they are not necessary in our system

    // New attributes below added to fit our game requirements
    private final int gridWidth;    // Custom gridWidth for each realm
    private final int gridHeight;   // Custom gridHeight for each realm
    private final Map<Item, Integer> lootTable; // Each realm has its own loot and loottable

    public Realm(String name, int gridWidth, int gridHeight, Map<Item, Integer> lootTable) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.lootTable = lootTable;
    }
    public String getName() {
        return name;
    }
    public UUID getID() {
        return id;
    }
    public int getGridWidth() {
        return gridWidth;
    }
    public int getGridHeight() {
        return gridHeight;
    }
    public Map<Item, Integer> getLootTable() {
        return lootTable;
    }
}

/*
ORIGINAL CLASS - FROM GABRIEL O'HARA A2

import java.util.*;

public class Realm {
    private final String name;
    private String description = null;
    private final UUID id = UUID.randomUUID();
    private Integer xCoordinate = null;
    private Integer yCoordinate = null;
    private static final Set<String> existingNames = new HashSet<>();

    public Realm(String name) {
        checkDuplicate(name);
        this.name = name;
    }

    public Realm(String name, String description) {
        checkDuplicate(name);
        this.name = name;
        this.description = description;
    }

    public Realm(String name, int xCoordinate, int yCoordinate) {
        checkDuplicate(name);
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public Realm(String name, String description, int xCoordinate, int yCoordinate) {
        checkDuplicate(name);
        this.name = name;
        this.description = description;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public UUID getID() {
        return id;
    }

    private static String normalize(String name) {
        return name.trim().toLowerCase();
    }

    private static void checkDuplicate(String name) {
        String key = normalize(name);
        if (existingNames.contains(key)) {
            throw new IllegalStateException("Realm name already exists: " + name);
        }
        existingNames.add(key);
    }

    public String getTitle() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Integer getXCoordinate() {
        return xCoordinate;
    }
    public Integer getYCoordinate() {
        return yCoordinate;
    }
}

 */