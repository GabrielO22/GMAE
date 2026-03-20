package profiles;

import characters.Character;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class PlayerProfile {
    private final UUID playerID;
    private String playerName;
    private int numRelicsCollected;
    private int duelsWon;
    private int duelsLost;
    private int selectedAvatarIndex;

    private final List<Character> characters;
    private final int MAX_CHARACTERS = 3;

    public PlayerProfile() {
        this.playerID = UUID.randomUUID();
        characters = new LinkedList<>();
        for (int i = 0; i < MAX_CHARACTERS; i++) {
            characters.add(new Character());
        }
        duelsWon = 0;
        duelsLost = 0;
        selectedAvatarIndex = 0;
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
        return numRelicsCollected;
    }

    public void addRelicCollected() {
        numRelicsCollected ++;
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

    public String getDuelRecord()  { return "Duel Record: " + duelsWon + "-" + duelsLost; }

    public int getSelectedAvatarIndex() {
        return selectedAvatarIndex;
    }

    public void setSelectedAvatarIndex(int index) {
        this.selectedAvatarIndex = index;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
