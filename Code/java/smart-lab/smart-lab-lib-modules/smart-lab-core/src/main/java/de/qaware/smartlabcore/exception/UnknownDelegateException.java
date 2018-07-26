package de.qaware.smartlabcore.exception;

public class UnknownDelegateException extends SmartLabException {

    public UnknownDelegateException() {
    }

    public UnknownDelegateException(String message) {
        super(message);
    }

    public UnknownDelegateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownDelegateException(Throwable cause) {
        super(cause);
    }

    public UnknownDelegateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
