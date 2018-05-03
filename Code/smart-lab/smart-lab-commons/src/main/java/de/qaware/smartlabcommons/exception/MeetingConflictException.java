package de.qaware.smartlabcommons.exception;

public class MeetingConflictException extends RuntimeException {

    public MeetingConflictException() {
    }

    public MeetingConflictException(String message) {
        super(message);
    }

    public MeetingConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public MeetingConflictException(Throwable cause) {
        super(cause);
    }

    public MeetingConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
