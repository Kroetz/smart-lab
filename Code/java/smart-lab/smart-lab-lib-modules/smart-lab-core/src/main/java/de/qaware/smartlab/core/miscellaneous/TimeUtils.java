package de.qaware.smartlab.core.miscellaneous;

import java.time.Instant;

import static java.lang.String.format;

public abstract class TimeUtils {

    public static boolean isBetween(Instant instantToCheck, Instant firstBoundary, Instant secondBoundary) {
        Instant from;
        Instant until;
        if(firstBoundary.isBefore(secondBoundary)) {
            from = firstBoundary;
            until = secondBoundary;
        }
        else {
            from = secondBoundary;
            until = firstBoundary;
        }
        return instantToCheck.isAfter(from) && instantToCheck.isBefore(until);
    }

    public static boolean isNowInProgress(Instant start, Instant end) {
        if(start.isAfter(end)) throw new IllegalArgumentException(format(
                "The start %s must be prior to the end %s",
                start.toString(),
                end.toString()));
        return isBetween(Instant.now(), start, end);
    }
}
