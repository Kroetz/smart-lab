package de.qaware.smartlabcore.exception;

public class LocalDeviceException extends SmartLabException {

    public LocalDeviceException() {
    }

    public LocalDeviceException(String message) {
        super(message);
    }

    public LocalDeviceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocalDeviceException(Throwable cause) {
        super(cause);
    }

    public LocalDeviceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
