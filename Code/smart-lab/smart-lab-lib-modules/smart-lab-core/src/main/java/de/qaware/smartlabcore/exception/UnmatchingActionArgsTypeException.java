package de.qaware.smartlabcore.exception;

public class UnmatchingActionArgsTypeException extends RuntimeException {

    public UnmatchingActionArgsTypeException() {
    }

    public UnmatchingActionArgsTypeException(String message) {
        super(message);
    }

    public UnmatchingActionArgsTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnmatchingActionArgsTypeException(Throwable cause) {
        super(cause);
    }

    public UnmatchingActionArgsTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
