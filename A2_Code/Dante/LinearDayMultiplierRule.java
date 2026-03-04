class LinearDayMultiplierRule implements LocalTimeRule {
    double multiplier;
    int offsetMinutes;

    public LinearDayMultiplierRule(double multiplier, int offsetMinutes) {
        this.multiplier = multiplier;
        this.offsetMinutes = offsetMinutes;
    }

    @Override
    public WorldClockTime toLocal(WorldClockTime world) {
        // Formula: LocalTime = (WorldTime * multiplier) + offset.
        long totalWorldMinutes = world.toTotalMinutes();
        long scaledMinutes = Math.round(totalWorldMinutes * multiplier) + offsetMinutes;
        return new WorldClockTime(scaledMinutes);
    }
}
