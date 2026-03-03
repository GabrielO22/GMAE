public class WeekView extends AbstractTimelineView {

    @Override
    protected int getStartTime(int currentWorldTime) {
        return (currentWorldTime / WorldClock.MINUTES_PER_WEEK) * WorldClock.MINUTES_PER_WEEK;
    }

    @Override
    protected int getEndTime(int currentWorldTime) {
        return getStartTime(currentWorldTime) + WorldClock.MINUTES_PER_WEEK;
    }

    @Override
    protected void printHeader(int currentWorldTime) {
        int currentWeek = (currentWorldTime / WorldClock.MINUTES_PER_WEEK) + 1;
        System.out.println("\n--- WEEK TIMELINE (Week " + currentWeek + ") ---");
    }
}