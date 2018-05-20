package de.qaware.smartlabcommons.exception;

public class ActionExecutionFailedException extends RuntimeException {

    public ActionExecutionFailedException() {
    }

    public ActionExecutionFailedException(String message) {
        super(message);
    }

    public ActionExecutionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionExecutionFailedException(Throwable cause) {
        super(cause);
    }

    public ActionExecutionFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
