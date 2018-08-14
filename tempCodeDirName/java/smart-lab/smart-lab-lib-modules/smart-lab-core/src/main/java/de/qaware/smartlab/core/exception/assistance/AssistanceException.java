package de.qaware.smartlab.core.exception.assistance;

import de.qaware.smartlab.core.exception.SmartLabException;

public class AssistanceException extends SmartLabException {

    public AssistanceException() {
    }

    public AssistanceException(String message) {
        super(message);
    }

    public AssistanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssistanceException(Throwable cause) {
        super(cause);
    }

    public AssistanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
