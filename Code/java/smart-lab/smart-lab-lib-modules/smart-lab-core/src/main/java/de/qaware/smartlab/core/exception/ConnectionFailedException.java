package de.qaware.smartlab.core.exception;

public class ConnectionFailedException extends SmartLabException {

    public ConnectionFailedException() {
    }

    public ConnectionFailedException(String message) {
        super(message);
    }

    public ConnectionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionFailedException(Throwable cause) {
        super(cause);
    }

    public ConnectionFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
