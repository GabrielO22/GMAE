import java.util.*;
public class Campaign {
    private final String name;
    private final UUID id;
    private final UUID ownerUserID;
    private final List<QuestEvent> events;
    private final Map<UUID, PermissionLevel> sharedWith;
    private Visibility visibility;

    public Campaign(String name, UUID ownerUserID, Visibility visibility) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.ownerUserID = ownerUserID;
        this.events = new LinkedList<>();
        this.sharedWith = new HashMap<>();
        this.visibility = visibility;
    }

    public void addEvent(QuestEvent event) {
        events.add(event);
    }
    public void removeEvent(QuestEvent event) {
        events.remove(event);
    }
    public void grantAccess(UUID userID, PermissionLevel perm) {
        sharedWith.put(userID, perm);
    }
    public void revokeAccess(UUID userID) {
        sharedWith.remove(userID);
    }

    public UUID getOwnerUserID() {
        return ownerUserID;
    }

    public Map<UUID, PermissionLevel> getSharedWith() {
        return sharedWith;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public UUID getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<QuestEvent> getEvents() {
        return events;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public QuestEvent getEventByName(String name) {
        for (QuestEvent event : events) {
            if (event.getName().equalsIgnoreCase(name)) {
                return event;
            }
        }
        return null;
    }
}