package de.qaware.smartlabcore.exception;

public class MinimalDurationReachedException extends SmartLabException {

    public MinimalDurationReachedException() {
    }

    public MinimalDurationReachedException(String message) {
        super(message);
    }

    public MinimalDurationReachedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinimalDurationReachedException(Throwable cause) {
        super(cause);
    }

    public MinimalDurationReachedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
