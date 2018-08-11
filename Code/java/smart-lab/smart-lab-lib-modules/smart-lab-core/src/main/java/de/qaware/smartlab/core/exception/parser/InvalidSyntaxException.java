package de.qaware.smartlab.core.exception.parser;

import de.qaware.smartlab.core.exception.SmartLabException;

public class InvalidSyntaxException extends SmartLabException {

    public InvalidSyntaxException() {
    }

    public InvalidSyntaxException(String message) {
        super(message);
    }

    public InvalidSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSyntaxException(Throwable cause) {
        super(cause);
    }

    public InvalidSyntaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
