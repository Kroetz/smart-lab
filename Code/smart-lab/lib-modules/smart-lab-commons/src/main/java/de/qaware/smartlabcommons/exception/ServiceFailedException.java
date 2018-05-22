package de.qaware.smartlabcommons.exception;

public class ServiceFailedException extends RuntimeException {

    public ServiceFailedException() {
    }

    public ServiceFailedException(String message) {
        super(message);
    }

    public ServiceFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceFailedException(Throwable cause) {
        super(cause);
    }

    public ServiceFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
