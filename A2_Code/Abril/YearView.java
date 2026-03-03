public class YearView extends AbstractTimelineView {

    @Override
    protected int getStartTime(int currentWorldTime) {
        return (currentWorldTime / WorldClock.MINUTES_PER_YEAR) * WorldClock.MINUTES_PER_YEAR;
    }

    @Override
    protected int getEndTime(int currentWorldTime) {
        return getStartTime(currentWorldTime) + WorldClock.MINUTES_PER_YEAR;
    }

    @Override
    protected void printHeader(int currentWorldTime) {
        int currentYear = (currentWorldTime / WorldClock.MINUTES_PER_YEAR) + 1;
        System.out.println("\n--- YEAR TIMELINE (Year " + currentYear + ") ---");
    }
}