package edu.uci.gmae.group16.profiles;

import java.util.*;
import edu.uci.gmae.group16.reuse.Character;

public class PlayerProfile {
    private final UUID playerID;
    private String playerName;
    private int relicsCollected;
    private int duelsWon;
    private int duelsLost;

    private final List<Character> characters;
    private final int MAX_CHARACTERS = 3;

    public PlayerProfile(UUID playerID) {
        this.playerID = playerID;
        characters = new LinkedList<>();
        for (int i = 0; i < MAX_CHARACTERS; i++) {
            characters.add(new Character());
        }
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name) {
        playerName = name;
    }

    public int getRelicsCollected() {
        return relicsCollected;
    }

    public void addRelicCollected() {
        relicsCollected ++;
    }

    public int getDuelsWon() {
        return duelsWon;
    }

    public void addDuelWon() {
        duelsWon ++;
    }

    public int getDuelsLost() {
        return duelsLost;
    }

    public void addDuelLost() {
        duelsLost ++;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
