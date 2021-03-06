package de.qaware.smartlab.core.exception.data;

public class MaximalDurationReachedException extends DataException {

    public MaximalDurationReachedException() {
    }

    public MaximalDurationReachedException(String message) {
        super(message);
    }

    public MaximalDurationReachedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaximalDurationReachedException(Throwable cause) {
        super(cause);
    }

    public MaximalDurationReachedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
