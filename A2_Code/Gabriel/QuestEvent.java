import java.util.*;
public class QuestEvent {
    private final String name;
    private final Realm realm;
    private final Set<UUID> participantCharacterIDs;
    private final Map<UUID, PermissionLevel> sharedWith;

    public QuestEvent(String name, Realm realm) {
        this.name = name;
        this.realm = realm;
        participantCharacterIDs = new HashSet<>();
        sharedWith = new HashMap<>();
    }
    public void addParticipant(UUID characterID) {
        participantCharacterIDs.add(characterID);
    }
    public void removeParticipant(UUID characterID) {
        participantCharacterIDs.remove(characterID);
    }
    public void grantAccess(UUID userID, PermissionLevel perm) {
        sharedWith.put(userID, perm);
    }
    public void revokeAccess(UUID userID) {
        sharedWith.remove(userID);
    }

    public Map<UUID, PermissionLevel> getSharedWith() {
        return sharedWith;
    }
    public String getName() {
        return name;
    }

    public Realm getRealm() {
        return realm;
    }

    public Set<UUID> getParticipantIDs() {
        return participantCharacterIDs;
    }
}
