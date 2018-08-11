package de.qaware.smartlab.core.exception.delegate;

import de.qaware.smartlab.core.exception.SmartLabException;

public class DelegateException extends SmartLabException {

    public DelegateException() {
    }

    public DelegateException(String message) {
        super(message);
    }

    public DelegateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DelegateException(Throwable cause) {
        super(cause);
    }

    public DelegateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
