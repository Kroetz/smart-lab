package de.qaware.smartlabcore.exception;

public class UnknownActionException extends SmartLabException {

    public UnknownActionException() {
    }

    public UnknownActionException(String message) {
        super(message);
    }

    public UnknownActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownActionException(Throwable cause) {
        super(cause);
    }

    public UnknownActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
