package de.qaware.smartlabcore.exception;

public class UnknownAssistanceException extends SmartLabException {

    public UnknownAssistanceException() {
    }

    public UnknownAssistanceException(String message) {
        super(message);
    }

    public UnknownAssistanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownAssistanceException(Throwable cause) {
        super(cause);
    }

    public UnknownAssistanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
