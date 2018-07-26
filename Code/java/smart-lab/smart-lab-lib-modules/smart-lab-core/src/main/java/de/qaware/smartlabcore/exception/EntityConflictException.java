package de.qaware.smartlabcore.exception;

public class EntityConflictException extends EntityCreationException {

    public EntityConflictException() {
    }

    public EntityConflictException(String message) {
        super(message);
    }

    public EntityConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityConflictException(Throwable cause) {
        super(cause);
    }

    public EntityConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
