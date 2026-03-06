package edu.uci.gmae.group16.profiles;

import java.util.*;

public class PlayerProfile {
    private final UUID playerID;
    private String playerName;
    private int relicsCollected;
    private int duelsWon;
    private int duelsLost;

    public PlayerProfile(UUID playerID) {
        this.playerID = playerID;
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
}
