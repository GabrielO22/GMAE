// Strategy design pattern added here

package GuildQuest.core;

public interface LocalTimeRule {
    ClockTime convertToLocalTime(ClockTime clockTime);
}