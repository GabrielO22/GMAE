import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class QuestEvent {
    UUID eventID;
    String title;
    WorldClockTime startTime;
    WorldClockTime endTime;
    Realm realm;

    List<Character> participants;
    List<InventoryChange> rewards;

    QuestEvent(String title, WorldClockTime start, WorldClockTime end, Realm realm) {
        this.eventID = UUID.randomUUID();
        this.title = title;
        this.startTime = start;
        this.endTime = end;
        this.realm = realm;
        this.participants = new ArrayList<>();
        this.rewards = new ArrayList<>();
    }

    public UUID getEventID() {
        return eventID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WorldClockTime getStartTime() {
        return startTime;
    }

    public void setStartTime(WorldClockTime startTime) {
        this.startTime = startTime;
    }

    public WorldClockTime getEndTime() {
        return endTime;
    }

    public void setEndTime(WorldClockTime endTime) {
        this.endTime = endTime;
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public List<Character> getParticipants() {
        return participants;
    }

    public List<InventoryChange> getRewards() {
        return rewards;
    }

    public void addParticipant(Character c) {
        participants.add(c);
    }

    public void addReward(InventoryChange change) {
        rewards.add(change);
    }

    public void onEventComplete() {
        for (InventoryChange change : rewards)
            change.execute();

        System.out.println("Event '" + title + "' completed. Rewards distributed.");
    }

    public boolean isEventFinished() {
        WorldClockTime worldTime = WorldClock.getInstance().getCurrentTime();
        WorldClockTime localTime = realm.getLocalTime(worldTime);

        return localTime.toTotalMinutes() >= endTime.toTotalMinutes();
    }
}