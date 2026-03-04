// Strategy design pattern added here

package GuildQuest.core;

public class ClockTime {

    int days;
    int hours;
    int minutes;

    public ClockTime(int days, int hours, int minutes) {
        // Creates a WorldClockTime object with custom time
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return days + " / " + hours + " / " + minutes;
    }
}
