package de.qaware.smartlabcore.exception;

public class UnknownServiceException extends SmartLabException {

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
