package de.qaware.smartlabcore.exception;

public class AssistanceTrackingException extends SmartLabException {

    public AssistanceTrackingException() {
    }

    public AssistanceTrackingException(String message) {
        super(message);
    }

    public AssistanceTrackingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssistanceTrackingException(Throwable cause) {
        super(cause);
    }

    public AssistanceTrackingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
