package de.qaware.smartlab.core.exception.filesystem;

import de.qaware.smartlab.core.exception.SmartLabException;

public class TempFileManagerException extends SmartLabException {

    public TempFileManagerException() {
    }

    public TempFileManagerException(String message) {
        super(message);
    }

    public TempFileManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TempFileManagerException(Throwable cause) {
        super(cause);
    }

    public TempFileManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
