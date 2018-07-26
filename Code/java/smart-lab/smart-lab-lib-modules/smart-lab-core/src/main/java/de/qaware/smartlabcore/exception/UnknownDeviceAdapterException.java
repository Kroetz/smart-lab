package de.qaware.smartlabcore.exception;

public class UnknownDeviceAdapterException extends SmartLabException {

    public UnknownDeviceAdapterException() {
    }

    public UnknownDeviceAdapterException(String message) {
        super(message);
    }

    public UnknownDeviceAdapterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownDeviceAdapterException(Throwable cause) {
        super(cause);
    }

    public UnknownDeviceAdapterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
