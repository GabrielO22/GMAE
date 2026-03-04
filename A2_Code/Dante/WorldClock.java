class WorldClock {
    private static WorldClock instance;
    private long totalElapsedMinutes;

    private WorldClock() {
        this.totalElapsedMinutes = 0;
    }

    public static WorldClock getInstance() {
        if (instance == null)
            instance = new WorldClock();
        return instance;
    }

    public void advanceTime(int mins) {
        this.totalElapsedMinutes += mins;
    }

    public WorldClockTime getCurrentTime() {
        return new WorldClockTime(totalElapsedMinutes);
    }
}
