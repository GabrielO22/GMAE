// Strategy design pattern added here
package GuildQuest.core;

public class FixedOffSetRule implements LocalTimeRule {
    private int offset;

    public FixedOffSetRule(int offset) {
        this.offset = offset;
    }

    public ClockTime convertToLocalTime(ClockTime ClockTime) {
        return new ClockTime(ClockTime.days, ClockTime.hours, ClockTime.minutes + offset);
    }

    public static FixedOffSetRule createRule(int offset) {
        return new FixedOffSetRule(offset);
    }

    @Override
    public String toString() {
        return "Fixed Offset - " + offset;
    }
}