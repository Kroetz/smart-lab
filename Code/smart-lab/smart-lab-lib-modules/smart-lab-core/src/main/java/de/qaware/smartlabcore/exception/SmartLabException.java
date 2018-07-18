package de.qaware.smartlabcore.exception;

public class SmartLabException extends RuntimeException {

    public SmartLabException() {
    }

    public SmartLabException(String message) {
        super(message);
    }

    public SmartLabException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmartLabException(Throwable cause) {
        super(cause);
    }

    public SmartLabException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
