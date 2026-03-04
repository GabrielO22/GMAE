package GuildQuest.core;

import java.util.List;
import java.util.ArrayList;

public class User {
    String userID;
    String userName;
    List<Campaign> campaignList;
    List<Character> characterList;

    public User(String userID) {
        this.userID = userID;
        campaignList = new ArrayList<>();
        characterList = new ArrayList<>();
    }

    public void createCampaign(Campaign c) {
        campaignList.add(c);
    }

    public void removeCampaign(Campaign c) {
        campaignList.remove(c);
    }

    public void addCharacter(Character c) {
        characterList.add(c);
    }

    public void removeCharacter(Character c) {
        characterList.remove(c);
    }

    public List<Campaign> getCampaigns() {
        return List.copyOf(campaignList);
    }

    public List<Character> getCharacters() {
        return List.copyOf(characterList);
    }
}