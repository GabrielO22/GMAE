import java.util.*;

public class GuildQuest {
    private final Map<UUID, User> usersByID;
    private final Map<String, User> usersByUsername;
    private final Map<UUID, Campaign> campaignsByID;
    private final Map<String, Campaign> campaignsByTitle;
    private final Map<UUID, Realm> realmsByID;
    private final Map<String, Realm> realmsByTitle;
    private final Map<String, Item> itemsByName;
    private final AuthorizationService authorizationService;


    public GuildQuest() {
        usersByID = new HashMap<>();
        usersByUsername = new HashMap<>();
        campaignsByID = new HashMap<>();
        campaignsByTitle = new HashMap<>();
        realmsByID = new HashMap<>();
        realmsByTitle = new HashMap<>();
        itemsByName = new HashMap<>();
        authorizationService = new AuthorizationService();
    }

    public void createUser(String username) {
        String key = username.toLowerCase().strip();

        if (usersByUsername.containsKey(key)) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User(username);
        usersByID.put(user.getID(), user);
        usersByUsername.put(key, user);
    }

    public boolean removeUser(String username) {
        if (username == null)
            return false;

        String key = username.toLowerCase().strip();
        User user = usersByUsername.get(key);

        if (user == null)
            return false;

        UUID userId = user.getID();

        for (Campaign c : campaignsByID.values()) {
            if (userId.equals(c.getOwnerUserID())) {
                throw new IllegalStateException(
                        "Cannot remove user '" + user.getName() + "': user owns one or more campaigns."
                );
            }
        }

        usersByUsername.remove(key);
        usersByID.remove(userId);

        return true;
    }

    public void createCampaign(User owner, String name, String visibilityString) {
        if (owner == null || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid campaign parameters");
        }
        if (campaignsByTitle.containsKey(name)) {
            throw new IllegalAccessError("Must have unique username");
        }
        Campaign campaign;
        if (visibilityString.equalsIgnoreCase("private"))
            campaign = new Campaign(name.trim(), owner.getID(), Visibility.PRIVATE);
        else if (visibilityString.equalsIgnoreCase("public"))
            campaign = new Campaign(name.trim(), owner.getID(), Visibility.PUBLIC);
        else
            throw new IllegalStateException("Invalid visibility parameter");
        campaignsByID.put(campaign.getID(), campaign);
        campaignsByTitle.put(campaign.getName(), campaign);
        owner.addCampaignByID(campaign.getID());
    }

    public void deleteCampaign(UUID campaignID) {
        if (campaignID == null) {
            throw new IllegalArgumentException("Campaign ID cannot be null");
        }

        Campaign removed = campaignsByID.remove(campaignID);
        if (removed == null) {
            throw new IllegalArgumentException("Campaign not found.");
        }
    }

    public void createRealm(String name, String description, Integer x, Integer y) {
        if (description == null && x == null && y == null) {
            Realm realm = new Realm(name.trim());
            realmsByID.put(realm.getID(), realm);
            realmsByTitle.put(name, realm);
        } else if (description == null && x != null && y != null) {
            Realm realm = new Realm(name.trim(), x, y);
            realmsByID.put(realm.getID(), realm);
            realmsByTitle.put(name, realm);
        } else if (description != null && x == null && y == null) {
            Realm realm = new Realm(name.trim(), description);
            realmsByID.put(realm.getID(), realm);
            realmsByTitle.put(name, realm);
        } else if (description != null && x != null && y != null) {
            Realm realm = new Realm(name.trim(), description, x, y);
            realmsByID.put(realm.getID(), realm);
            realmsByTitle.put(name, realm);
        } else {
            throw new IllegalArgumentException("Arguments are wrong");
        }
    }

    public void deleteRealm(UUID realmID) {
        realmsByTitle.remove(realmsByID.get(realmID).getTitle());
        realmsByID.remove(realmID);
    }

    public Map<UUID, User> getUsersByID() {
        return usersByID;
    }
    public Map<String, User> getUsersByUsername() {
        return usersByUsername;
    }
    public Map<String, Realm> getRealmsByTitle() {

        return realmsByTitle;
    }
    public Map<UUID, Campaign> getCampaignsByID() {
        return campaignsByID;
    }
    public Map<String, Campaign> getCampaignsByTitle() {
        return campaignsByTitle;
    }
    public Map<String, Item> getItemsByName() {
        return itemsByName;
    }
    public AuthorizationService getAuthorizationService() {
        return authorizationService;
    }
}
