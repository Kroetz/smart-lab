package de.qaware.smartlab.core.exception.context;

import de.qaware.smartlab.core.exception.SmartLabException;

public class InsufficientContextException extends SmartLabException {

    public InsufficientContextException() {
    }

    public InsufficientContextException(String message) {
        super(message);
    }

    public InsufficientContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientContextException(Throwable cause) {
        super(cause);
    }

    public InsufficientContextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
