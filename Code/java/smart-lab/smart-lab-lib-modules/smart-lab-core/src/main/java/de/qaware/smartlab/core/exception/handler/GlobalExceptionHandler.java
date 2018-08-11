package de.qaware.smartlab.core.exception.handler;

import de.qaware.smartlab.core.exception.entity.EntityConflictException;
import de.qaware.smartlab.core.exception.entity.EntityNotFoundException;
import de.qaware.smartlab.core.exception.entity.MaximalDurationReachedException;
import de.qaware.smartlab.core.exception.entity.MinimalDurationReachedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(value = CONFLICT, reason = "An entity with the specified ID already exists")
    @ExceptionHandler(EntityConflictException.class)
    public void handleEntityConflictException() {
        logConversion(
                EntityConflictException.class,
                CONFLICT.value(),
                CONFLICT.name());
    }

    @ResponseStatus(value = NOT_FOUND, reason = "An entity with the specified ID does not exist")
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException() {
        logConversion(
                EntityNotFoundException.class,
                NOT_FOUND.value(),
                NOT_FOUND.name());
    }

    @ResponseStatus(value = UNPROCESSABLE_ENTITY, reason = "The specified event already reached its minimal duration")
    @ExceptionHandler(MinimalDurationReachedException.class)
    public void handleMinimalDurationReachedException() {
        logConversion(
                MinimalDurationReachedException.class,
                UNPROCESSABLE_ENTITY.value(),
                UNPROCESSABLE_ENTITY.name());
    }

    @ResponseStatus(value = UNPROCESSABLE_ENTITY, reason = "The specified event already reached its maximal duration")
    @ExceptionHandler(MaximalDurationReachedException.class)
    public void handleMaximalDurationReachedException() {
        logConversion(
                MaximalDurationReachedException.class,
                UNPROCESSABLE_ENTITY.value(),
                UNPROCESSABLE_ENTITY.name());
    }

    private void logConversion(Class<? extends Exception> exceptionClass, int httpStatusCode, String httpStatusName) {
        log.warn(format(
                "Converted exception \"%s\" to HTTP status code %d %s",
                exceptionClass.getSimpleName(),
                httpStatusCode,
                httpStatusName));
    }
}
