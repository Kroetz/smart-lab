package de.qaware.smartlab.core.exception;

public class LocalActuatorException extends SmartLabException {

    public LocalActuatorException() {
    }

    public LocalActuatorException(String message) {
        super(message);
    }

    public LocalActuatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocalActuatorException(Throwable cause) {
        super(cause);
    }

    public LocalActuatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
