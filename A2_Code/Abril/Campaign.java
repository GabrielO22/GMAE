import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import java.util.Collections;

public class Campaign {
    private final String campaignId;
    private String campaignName; // Removed final to allow renaming
    private final User owner;
    private final Set<QuestEvent> ownedQuestEvents;
    private final Set<CampaignShare> sharedUsers;

    private boolean archived = false;

    public enum VisibilitySetting { PUBLIC, PRIVATE }
    private VisibilitySetting visibility = VisibilitySetting.PRIVATE;

    public Campaign(String campaignId, String campaignName, User owner) {
        this.campaignId = campaignId;
        this.campaignName = campaignName;
        this.owner = owner;
        this.ownedQuestEvents = new HashSet<>();
        this.sharedUsers = new HashSet<>();
    }

    // QuestEvent Management
    public void addQuestEvent(QuestEvent event) {
        ownedQuestEvents.add(event);
    }

    public void removeQuestEvent(QuestEvent event) {
        if (ownedQuestEvents.remove(event)) {
            System.out.println("Removed event: " + event.getQuestEventId());
        } else {
            System.out.println("Event not found.");
        }
    }

    public Set<QuestEvent> findEventsInRange(int start, int end) {
        Set<QuestEvent> result = new HashSet<>();
        for (QuestEvent qe : ownedQuestEvents) {
            if (qe.getStartTime() >= start && qe.getEndTime() <= end) {
                result.add(qe);
            }
        }
        return result;
    }

    // Campaign Management
    public void rename(String newName) {
        this.campaignName = newName;
    }

    public void setVisibility(VisibilitySetting visibility) {
        this.visibility = visibility;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    // Sharing Logic
    public void shareCampaign(User friend, PermissionLevel level) {
        CampaignShare share = new CampaignShare(this, friend, level);
        this.sharedUsers.add(share);
        friend.addSharedCampaign(this);
    }

    // Getters
    public String getCampaignId() { return campaignId; }
    public String getCampaignName() { return campaignName; }
    public User getOwner() { return owner; }
    public boolean isArchived() { return archived; }
    public Set<QuestEvent> getOwnedQuestEvents() { return Collections.unmodifiableSet(ownedQuestEvents); }

    // Hashset overrides for character id comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campaign campaign = (Campaign) o;
        return Objects.equals(campaignId, campaign.campaignId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaignId);
    }
}