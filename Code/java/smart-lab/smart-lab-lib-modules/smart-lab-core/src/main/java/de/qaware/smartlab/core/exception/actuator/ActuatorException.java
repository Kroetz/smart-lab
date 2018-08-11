package de.qaware.smartlab.core.exception.actuator;

import de.qaware.smartlab.core.exception.SmartLabException;

public class ActuatorException extends SmartLabException {

    public ActuatorException() {
    }

    public ActuatorException(String message) {
        super(message);
    }

    public ActuatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActuatorException(Throwable cause) {
        super(cause);
    }

    public ActuatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
