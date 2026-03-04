class WorldClockTime {
    static final int MINUTES_IN_HOUR = 60;
    static final int HOURS_IN_DAY = 24;
    static final int MINUTES_IN_DAY = HOURS_IN_DAY * MINUTES_IN_HOUR;
    int day;
    int hour;
    int minute;

    WorldClockTime(int day, int hour, int minute) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    WorldClockTime(long totalMinutes) {
        this.day = (int) (totalMinutes / (MINUTES_IN_DAY));
        long remainingMinutes = totalMinutes % (MINUTES_IN_DAY);
        this.hour = (int) (remainingMinutes / MINUTES_IN_HOUR);
        this.minute = (int) (remainingMinutes % MINUTES_IN_HOUR);
    }

    public long toTotalMinutes() {
        return minute + (day * HOURS_IN_DAY + hour) * MINUTES_IN_HOUR;
    }

    @Override
    public String toString() {
        return String.format("Day %d, %02d:%02d", day, hour, minute);
    }
}