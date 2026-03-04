// Singleton design pattern added here
package GuildQuest.core;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class WorldTime {
    private static WorldTime worldTime;

    private static final Instant WORLD_EPOCH = Instant.parse("2026-01-01T00:00:00Z");
    private static final int MINUTES_PER_DAY = 1440;
    private static final int MINUTES_PER_HOUR = 60;

    private WorldTime() {
    }

    public static WorldTime getInstance() {
        if (worldTime == null)
            worldTime = new WorldTime();
        return worldTime;
    }

    public ClockTime getCurrentTime() {
        Instant currentTime = Clock.systemUTC().instant();
        long totalMinutes = Duration.between(WORLD_EPOCH, currentTime).toMinutes();

        int days = (int) (totalMinutes / MINUTES_PER_DAY);
        int remainingMinutes = (int) (totalMinutes % MINUTES_PER_DAY);

        int hours = remainingMinutes / MINUTES_PER_HOUR;
        int minutes = remainingMinutes % MINUTES_PER_HOUR;

        return new ClockTime(days, hours, minutes);
    }
}
