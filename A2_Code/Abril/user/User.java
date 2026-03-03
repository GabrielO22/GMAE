package user;

import campaign.Campaign;
import campaign.QuestEvent;
import main.Settings;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class User {
    private final String userId;
    private final String username;
    private final String email;

    private final Set<Campaign> ownedCampaigns;
    private final Set<Campaign> sharedCampaigns;
    private final Set<QuestEvent> sharedEvents;
    private final Set<Character> ownedCharacters; // Added missing field
    private final Settings settings;

    // may want to modify in future to allow chnages to user info
    public User(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.ownedCampaigns = new HashSet<>();
        this.sharedCampaigns = new HashSet<>();
        this.sharedEvents = new HashSet<>();
        this.ownedCharacters = new HashSet<>();
        this.settings = new Settings(); // Initialize default settings
    }

    // Getters
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public Settings getSettings() { return settings; }
    public String getEmail() { return email; }
    public Set<Campaign> getOwnedCampaigns() {
        return Collections.unmodifiableSet(ownedCampaigns); // for display
    }
    public Set<Character> getOwnedCharacters() {
        return Collections.unmodifiableSet(ownedCharacters); // for display
    }


    // campaign.Campaign management
    public void addCampaign(Campaign campaign) {
        ownedCampaigns.add(campaign);
    }

    public void removeCampaign(Campaign campaign) {
        if (this.ownedCampaigns.remove(campaign)) {
            System.out.println("Successfully removed campaign with ID " + campaign.getCampaignId());
        } else {
            System.out.println("Could not remove: campaign.Campaign ID " + campaign.getCampaignId() + " not found.");
        }
    }

    public void addSharedCampaign(Campaign campaign) {
        sharedCampaigns.add(campaign);
    }

    public void addSharedEvent(QuestEvent event) {
        sharedEvents.add(event);
    }

    // user.Character management
    public void addCharacter(Character character) {
        ownedCharacters.add(character);
    }

    public void removeCharacter(Character character) {
        if (this.ownedCharacters.remove(character)) {
            System.out.println("Successfully removed character with ID " + character.getCharacterId());
        } else {
            System.out.println("Could not remove: user.Character ID " + character.getCharacterId() + " not found.");
        }
    }

    // may need to update to hashmap for frequent searches
    public Character findCharacter(String characterId) {
        return this.ownedCharacters.stream()
                .filter(c -> c.getCharacterId().equals(characterId))
                .findFirst()
                .orElse(null);
    }

    // Clear lists
    public void clearCampaigns() {
        this.ownedCampaigns.clear();
    }

    public void clearCharacters() {
        this.ownedCharacters.clear();
    }
}