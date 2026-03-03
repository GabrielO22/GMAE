public class DayView extends AbstractTimelineView {

    @Override
    protected int getStartTime(int currentWorldTime) {
        return (currentWorldTime / WorldClock.MINUTES_PER_DAY) * WorldClock.MINUTES_PER_DAY;
    }

    @Override
    protected int getEndTime(int currentWorldTime) {
        return getStartTime(currentWorldTime) + WorldClock.MINUTES_PER_DAY;
    }

    @Override
    protected void printHeader(int currentWorldTime) {
        int currentDay = (currentWorldTime / WorldClock.MINUTES_PER_DAY) + 1;
        System.out.println("\n--- DAILY TIMELINE (Day " + currentDay + ") ---");
    }
}