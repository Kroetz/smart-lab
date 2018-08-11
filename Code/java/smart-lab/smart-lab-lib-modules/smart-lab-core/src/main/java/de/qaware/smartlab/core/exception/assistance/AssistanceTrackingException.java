package de.qaware.smartlab.core.exception.assistance;

import de.qaware.smartlab.core.exception.SmartLabException;

public class AssistanceTrackingException extends AssistanceException {

    public AssistanceTrackingException() {
    }

    public AssistanceTrackingException(String message) {
        super(message);
    }

    public AssistanceTrackingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssistanceTrackingException(Throwable cause) {
        super(cause);
    }

    public AssistanceTrackingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
