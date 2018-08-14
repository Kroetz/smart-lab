package de.qaware.smartlab.core.exception.resolver;

import de.qaware.smartlab.core.exception.SmartLabException;

public class ResolverException extends SmartLabException {

    public ResolverException() {
    }

    public ResolverException(String message) {
        super(message);
    }

    public ResolverException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResolverException(Throwable cause) {
        super(cause);
    }

    public ResolverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
