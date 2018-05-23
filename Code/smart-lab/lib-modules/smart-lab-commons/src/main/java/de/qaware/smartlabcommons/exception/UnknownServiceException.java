package de.qaware.smartlabcommons.exception;

public class UnknownServiceException extends RuntimeException {

    public UnknownServiceException() {
    }

    public UnknownServiceException(String message) {
        super(message);
    }

    public UnknownServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownServiceException(Throwable cause) {
        super(cause);
    }

    public UnknownServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
