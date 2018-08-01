package de.qaware.smartlabcore.exception;

public class DataSetException extends SmartLabException {

    public DataSetException() {
    }

    public DataSetException(String message) {
        super(message);
    }

    public DataSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSetException(Throwable cause) {
        super(cause);
    }

    public DataSetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
