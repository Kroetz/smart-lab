package de.qaware.smartlabcore.miscellaneous;

import java.time.Instant;

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
}
