import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class QuestEvent {
    private final String questEventId;
    private String questEventName;
    private String questEventDescription;
    private int startTime;
    private int endTime;
    private Realm realm;
    private QuestState currentState;

    private final List<Character> participants;
    private final Map<Item, Integer> loot;
    private final List<EventShare> shares;

    public QuestEvent(String questEventId, String questEventName, String description, int startTime, Realm realm) {
        this.questEventId = questEventId;
        this.questEventName = questEventName;
        this.questEventDescription = description;
        this.startTime = startTime;
        this.realm = realm;
        this.currentState = new NotStartedState();

        this.participants = new ArrayList<>();
        this.loot = new HashMap<>();
        this.shares = new ArrayList<>();
    }

    // participant management
    public void addParticipant(Character character) {
        participants.add(character);
    }
    public void removeParticipants(Character character){
        participants.remove(character);
    }

    // loot management
    public void addLootItem(Item item, int quantity) {
        if (quantity <= 0) return;
        // Merge: If exists, add quantity. If not, set quantity.
        this.loot.merge(item, quantity, Integer::sum);
        System.out.println("Added " + quantity + " " + item.getItemName() + "(s) to quest loot.");
    }

    public void removeLootItem(Item item, int quantity) {
        if (this.loot.containsKey(item)) {
            int currentQty = this.loot.get(item);
            if (currentQty <= quantity) {
                this.loot.remove(item);
                System.out.println("Removed all " + item.getItemName() + " from quest loot.");
            } else {
                this.loot.put(item, currentQty - quantity);
                System.out.println("Decreased " + item.getItemName() + " by " + quantity + ".");
            }
        } else {
            System.out.println("Item not found in quest loot.");
        }
    }

    public void distributeLoot() {
        System.out.println("Distributing loot for quest: " + questEventName);
        if (participants.isEmpty()) {
            System.out.println("No participants to receive loot.");
            return;
        }

        for (Character c : participants) {
            c.grantLoot(this.loot);
            System.out.println("Granted loot to " + c.getName());
        }
    }


    // manage event state
    public void setState(QuestState state) { this.currentState = state; }
    public QuestState getCurrentState() { return this.currentState; }

    public void startQuest() { currentState.start(this); }
    public void completeQuest() { currentState.complete(this); }
    public void failQuest() { currentState.fail(this); }


    public void formatDisplayTime(Settings.TimeDisplayMode mode) {}
    public void updateTimes(int time){}

    // Getters & Setters
    public String getQuestEventId() { return questEventId; }
    public String getQuestEventName() { return questEventName; }
    public int getStartTime() { return startTime; }
    public int getEndTime() { return endTime; }
    public Realm getRealm() { return realm; }

    public void setRealm(Realm realm) { this.realm = realm; }
    public void setTimes(int start, int end) { this.startTime = start; this.endTime = end; }


    public void shareEvent(User friend, PermissionLevel permission) {
        EventShare share = new EventShare(this, friend, permission);
        this.shares.add(share);
        friend.addSharedEvent(this);
    }

}