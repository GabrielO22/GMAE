package time.timeline;

import time.WorldClock;

public class MonthView extends AbstractTimelineView {

    @Override
    protected int getStartTime(int currentWorldTime) {
        return (currentWorldTime / WorldClock.MINUTES_PER_MONTH) * WorldClock.MINUTES_PER_MONTH;
    }

    @Override
    protected int getEndTime(int currentWorldTime) {
        return getStartTime(currentWorldTime) + WorldClock.MINUTES_PER_MONTH;
    }

    @Override
    protected void printHeader(int currentWorldTime) {
        int currentMonth = (currentWorldTime / WorldClock.MINUTES_PER_MONTH) + 1;
        System.out.println("\n--- MONTH TIMELINE (Month " + currentMonth + ") ---");
    }
}