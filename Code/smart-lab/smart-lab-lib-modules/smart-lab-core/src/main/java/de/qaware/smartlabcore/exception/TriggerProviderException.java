package de.qaware.smartlabcore.exception;

public class TriggerProviderException extends RuntimeException {

    public TriggerProviderException() {
    }

    public TriggerProviderException(String message) {
        super(message);
    }

    public TriggerProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public TriggerProviderException(Throwable cause) {
        super(cause);
    }

    public TriggerProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
