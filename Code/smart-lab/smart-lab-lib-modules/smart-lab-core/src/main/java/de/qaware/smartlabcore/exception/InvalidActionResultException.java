package de.qaware.smartlabcore.exception;

public class InvalidActionResultException extends SmartLabException {

    public InvalidActionResultException() {
    }

    public InvalidActionResultException(String message) {
        super(message);
    }

    public InvalidActionResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidActionResultException(Throwable cause) {
        super(cause);
    }

    public InvalidActionResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
