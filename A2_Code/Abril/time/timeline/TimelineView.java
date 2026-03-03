package time.timeline;

import campaign.QuestEvent;
import user.User;

import java.util.List;

public interface TimelineView {
    /**
     * Renders the list of events based on the view's specific time range rule
     * (Day, Week, etc.) relative to the current World Clock time.
     */
    void display(List<QuestEvent> events, User viewer, int currentWorldTime);
}