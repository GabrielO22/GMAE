import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

enum Visibility {
    PUBLIC,
    PRIVATE
}

class Campaign {
    UUID campaignID;
    String name;
    boolean isArchived;
    Visibility visibility;
    List<QuestEvent> events;
    List<CampaignUpdateListener> listeners;

    Campaign(String name) {
        this.campaignID = UUID.randomUUID();
        this.name = name;
        this.isArchived = false;
        this.visibility = Visibility.PRIVATE;
        this.events = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public UUID getCampaignID() {
        return campaignID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public List<QuestEvent> getEvents() {
        return events;
    }

    public void archive() {
        this.isArchived = true;
    }

    public void unarhive() {
        this.isArchived = false;
    }

    public QuestEvent getEventByID(UUID eventID) {
        for (int i = 0; i < this.events.size(); ++i) {
            if (this.events.get(i).getEventID().equals(eventID))
                return this.events.get(i);
        }

        return null;
    }

    public void addEvent(QuestEvent newEvent) {
        this.events.add(newEvent);
        this.notifyListeners();
    }

    public void removeEvent(UUID eventID) {
        QuestEvent targetEvent = getEventByID(eventID);
        if (targetEvent != null)
            this.events.remove(targetEvent);
    }

    public void addListener(CampaignUpdateListener l) {
        listeners.add(l);
    }

    private void notifyListeners() {
        for (CampaignUpdateListener l : listeners)
            l.onChange();
    }
}
