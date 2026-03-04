class FixedOffsetRule implements LocalTimeRule {
    int offsetMinutes;

    FixedOffsetRule(int offsetMinutes) {
        this.offsetMinutes = offsetMinutes;
    }

    public int getOffsetMinutes() {
        return offsetMinutes;
    }

    public void setOffsetMinutes(int offsetMinutes) {
        this.offsetMinutes = offsetMinutes;
    }

    @Override
    public WorldClockTime toLocal(WorldClockTime world) {
        return new WorldClockTime(world.toTotalMinutes() + offsetMinutes);
    }
}
