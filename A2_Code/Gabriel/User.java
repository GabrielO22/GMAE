import java.util.*;

public class User {
    private final UUID id;
    private final String name;
    private final Set<UUID> ownedCampaignIDs;
    private final List<Character> characters;

    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.ownedCampaignIDs = new HashSet<>();
        this.characters = new LinkedList<>();
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void deleteCharacter(Character character) {
        characters.remove(character);
    }

    public void addCampaignByID(UUID id) {
        ownedCampaignIDs.add(id);
    }

    public void removeCampaignByID(UUID id) {
        ownedCampaignIDs.remove(id);
    }

    public UUID getID() {
        return id;
    }

    public String getName() {
        return name;
    }
    public Set<UUID> getOwnedCampaignIDs() {
        return ownedCampaignIDs;
    }
    public List<Character> getCharacters() {
        return characters;
    }

    public Character getCharactersByName(String name) {
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public Character getCharactersByID(UUID id) {
        for (Character c : characters) {
            if (c.getID() == id) {
                return c;
            }
        }
        return null;
    }
}



