package time.timeline;

import campaign.QuestEvent;
import main.Settings;
import time.WorldClock;
import user.User;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractTimelineView implements TimelineView {

    @Override
    public final void display(List<QuestEvent> events, User viewer, int currentWorldTime) {
        printHeader(currentWorldTime);

        // individual views will calculate their own times
        int start = getStartTime(currentWorldTime);
        int end = getEndTime(currentWorldTime);

        events.stream()
                .filter(e -> e.getStartTime() >= start && e.getStartTime() < end)
                .sorted(Comparator.comparingInt(QuestEvent::getStartTime))
                .forEach(e -> printEvent(e, viewer));
    }

    // must be implemented by sub classes
    protected abstract int getStartTime(int currentWorldTime);
    protected abstract int getEndTime(int currentWorldTime);
    protected abstract void printHeader(int currentWorldTime);

    // extracted print method
    protected void printEvent(QuestEvent e, User viewer) {
        String timeString;
        WorldClock clock = WorldClock.getInstance();

        // Check user settings for time display preference
        if (viewer.getSettings().getDisplayPreference() == Settings.TimeDisplayMode.REALM_LOCAL_CLOCK
                && e.getRealm() != null) {

            int localTime = e.getRealm().convertWorldToLocal(e.getStartTime());
            timeString = clock.getFormattedTime(localTime) + " (" + e.getRealm().getRealmName() + ")";
        } else {
            timeString = clock.getFormattedTime(e.getStartTime()) + " (World Time)";
        }

        System.out.println("[" + timeString + "] " + e.getQuestEventId() + ": " + e.getQuestEventName());
    }
}