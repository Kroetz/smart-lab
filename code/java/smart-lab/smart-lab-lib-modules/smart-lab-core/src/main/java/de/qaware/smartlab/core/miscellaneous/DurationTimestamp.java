package de.qaware.smartlab.core.miscellaneous;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Set;

import static java.lang.String.format;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDurationHMS;

@EqualsAndHashCode
@Slf4j
public class DurationTimestamp {

    private static final long MILLIS_PER_SECOND = 1000;
    private static final long SECONDS_PER_MINUTE = 60;
    private static final long MINUTES_PER_HOUR = 60;

    private final long startMillisecond;
    private final Duration duration;

    private DurationTimestamp(long startMillisecond, long endMillisecond) {
        this.startMillisecond = startMillisecond;
        this.duration = Duration.ofMillis(endMillisecond - startMillisecond);
    }

    private DurationTimestamp(long startMillisecond, Duration duration) {
        this.startMillisecond = startMillisecond;
        this.duration = duration;
    }

    public static DurationTimestamp ofMillis(long startMillisecond, long endMillisecond) {
        return new DurationTimestamp(startMillisecond, endMillisecond);
    }

    public static DurationTimestamp ofSeconds(double startSecond, double endSecond) {
        return ofMillis((long) (startSecond * MILLIS_PER_SECOND), (long) (endSecond * MILLIS_PER_SECOND));
    }

    public static DurationTimestamp ofMinutes(double startMinute, double endMinute) {
        return ofSeconds(startMinute * SECONDS_PER_MINUTE, endMinute * SECONDS_PER_MINUTE);
    }

    public static DurationTimestamp ofHours(double startHour, double endHour) {
        return ofMinutes(startHour * MINUTES_PER_HOUR, endHour * MINUTES_PER_HOUR);
    }

    private static DurationTimestamp ofMillis(long startMillisecond, Duration duration) {
        return new DurationTimestamp(startMillisecond, duration);
    }

    public static DurationTimestamp ofSeconds(double startSecond, Duration duration) {
        return ofMillis((long) (startSecond * MILLIS_PER_SECOND), duration);
    }

    public static DurationTimestamp ofMinutes(double startMinute, Duration duration) {
        return ofSeconds(startMinute * SECONDS_PER_MINUTE, duration);
    }

    public static DurationTimestamp ofHours(double startHour, Duration duration) {
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

    public static DurationTimestamp enclosingTimestamp(Set<DurationTimestamp> timestamps) {
        Long startInMillis = timestamps.stream()
                .mapToLong(DurationTimestamp::getStartInMillis)
                .min()
                .orElse(0);
        Long endInMillis = timestamps.stream()
                .mapToLong(DurationTimestamp::getEndInMillis)
                .max()
                .orElse(0);
        return ofMillis(startInMillis, endInMillis);
    }
}
