package de.qaware.smartlabcommons.exception;

public class InsufficientContextException extends RuntimeException {

    public InsufficientContextException() {
    }

    public InsufficientContextException(String message) {
        super(message);
    }

    public InsufficientContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientContextException(Throwable cause) {
        super(cause);
    }

    public InsufficientContextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}