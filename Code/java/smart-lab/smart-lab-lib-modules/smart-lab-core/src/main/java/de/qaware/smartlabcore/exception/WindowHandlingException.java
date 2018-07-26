package de.qaware.smartlabcore.exception;

public class WindowHandlingException extends SmartLabException {

    public WindowHandlingException() {
    }

    public WindowHandlingException(String message) {
        super(message);
    }

    public WindowHandlingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WindowHandlingException(Throwable cause) {
        super(cause);
    }

    public WindowHandlingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
