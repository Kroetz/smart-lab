package de.qaware.smartlab.core.miscellaneous;

import java.time.Duration;

import static java.lang.String.format;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDurationHMS;

public class StartedDuration {

    private static final long MILLIS_PER_SECOND = 1000;
    private static final long SECONDS_PER_MINUTE = 60;
    private static final long MINUTES_PER_HOUR = 60;

    private final long startMillisecond;
    private final Duration duration;

    private StartedDuration(long startMillisecond, long endMillisecond) {
        this.startMillisecond = startMillisecond;
        this.duration = Duration.ofMillis(endMillisecond - startMillisecond);
    }

    private StartedDuration(long startMillisecond, Duration duration) {
        this.startMillisecond = startMillisecond;
        this.duration = duration;
    }

    public static StartedDuration ofMillis(long startMillisecond, long endMillisecond) {
        return new StartedDuration(startMillisecond, endMillisecond);
    }

    public static StartedDuration ofSeconds(double startSecond, double endSecond) {
        return ofMillis((long) (startSecond * MILLIS_PER_SECOND), (long) (endSecond * MILLIS_PER_SECOND));
    }

    public static StartedDuration ofMinutes(double startMinute, double endMinute) {
        return ofSeconds(startMinute * SECONDS_PER_MINUTE, endMinute * SECONDS_PER_MINUTE);
    }

    public static StartedDuration ofHours(double startHour, double endHour) {
        return ofMinutes(startHour * MINUTES_PER_HOUR, endHour * MINUTES_PER_HOUR);
    }

    private static StartedDuration ofMillis(long startMillisecond, Duration duration) {
        return new StartedDuration(startMillisecond, duration);
    }

    public static StartedDuration ofSeconds(double startSecond, Duration duration) {
        return ofMillis((long) (startSecond * MILLIS_PER_SECOND), duration);
    }

    public static StartedDuration ofMinutes(double startMinute, Duration duration) {
        return ofSeconds(startMinute * SECONDS_PER_MINUTE, duration);
    }

    public static StartedDuration ofHours(double startHour, Duration duration) {
        return ofMinutes(startHour * MINUTES_PER_HOUR, duration);
    }

    public long getStartInMillis() {
        return this.startMillisecond;
    }

    public long getEndInMillis() {
        return this.startMillisecond + duration.toMillis();
    }

    @Override
    public String toString() {
        return format(
                "[%s - %s]",
                formatDurationHMS(startMillisecond),
                formatDurationHMS(startMillisecond + this.duration.toMillis()));
    }
}
