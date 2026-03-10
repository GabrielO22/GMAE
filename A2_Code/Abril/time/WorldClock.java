package time;

public class WorldClock {
    private static WorldClock instance;
    private int currentWorldMinutes;

    // Constants
    public static final int MINUTES_PER_HOUR = 60;
    public static final int HOURS_PER_DAY = 24;
    public static final int MINUTES_PER_DAY = 1440; // 24 * 60
    public static final int MINUTES_PER_WEEK = 10080; // 1440 * 7
    public static final int MINUTES_PER_MONTH = 43200; // 1440 * 30
    public static final int MINUTES_PER_YEAR = 518400; // 1440 * 30 * 12

    private WorldClock() {
        this.currentWorldMinutes = 0; // Start at Day 1, 00:00 (Logic: 0 = start of Day 1)
    }

    public static synchronized WorldClock getInstance() {
        if (instance == null) {
            instance = new WorldClock();
        }
        return instance;
    }

    public void advanceTime(int minutes) {
        if (minutes > 0) {
            this.currentWorldMinutes += minutes;
        }
    }

    public int getCurrentTime() {
        return currentWorldMinutes;
    }

    /**
     * Converts a raw minute timestamp into a formatted string.
     * Example: 1500 mins -> "Day 2, 01:00"
     */
    //parseToComponents uml
    public String getFormattedTime(int totalMinutes) {
        int day = (totalMinutes / MINUTES_PER_DAY) + 1;
        int remainingMinutes = totalMinutes % MINUTES_PER_DAY;
        int hour = remainingMinutes / MINUTES_PER_HOUR;
        int minute = remainingMinutes % MINUTES_PER_HOUR;

        return String.format("Day %d, %02d:%02d", day, hour, minute);
    }
}