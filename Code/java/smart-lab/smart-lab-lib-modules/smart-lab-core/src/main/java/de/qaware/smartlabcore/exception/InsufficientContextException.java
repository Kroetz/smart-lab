package de.qaware.smartlabcore.exception;

public class InsufficientContextException extends SmartLabException {

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
