package de.qaware.smartlabcore.exception;

public class AssistanceFailedException extends RuntimeException {

    public AssistanceFailedException() {
    }

    public AssistanceFailedException(String message) {
        super(message);
    }

    public AssistanceFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssistanceFailedException(Throwable cause) {
        super(cause);
    }

    public AssistanceFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
