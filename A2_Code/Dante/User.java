import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class User {
    UUID userID;
    String displayName;
    Settings settings;
    List<Campaign> campaigns;
    List<Character> characters;

    User(String displayName) {
        this.userID = UUID.randomUUID();
        this.displayName = displayName;
        this.settings = new Settings();
        this.campaigns = new ArrayList<>();
        this.characters = new ArrayList<>();
    }

    public UUID getUserID() {
        return userID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Settings getSettings() {
        return settings;
    }

    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public Campaign getCampaignByID(UUID campaignID) {
        for (int i = 0; i < this.campaigns.size(); ++i) {
            if (this.campaigns.get(i).getCampaignID().equals(campaignID))
                return this.campaigns.get(i);
        }

        return null;
    }

    public Campaign createCampaign(String name) {
        Campaign newCampaign = new Campaign(name);
        this.campaigns.add(newCampaign);
        return newCampaign;
    }

    public void removeCampaign(UUID campaignID) {
        Campaign targetCampaign = getCampaignByID(campaignID);
        if (targetCampaign != null)
            this.campaigns.remove(targetCampaign);
    }

    public Character createCharacter(String name, String clazz) {
        Character newCharacter = new Character(name, clazz);
        this.characters.add(newCharacter);
        return newCharacter;
    }
}
