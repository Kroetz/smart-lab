package de.qaware.smartlab.core.exception.data;

public class NoCurrentEventException extends DataException {

    public NoCurrentEventException() {
    }

    public NoCurrentEventException(String message) {
        super(message);
    }

    public NoCurrentEventException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCurrentEventException(Throwable cause) {
        super(cause);
    }

    public NoCurrentEventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
