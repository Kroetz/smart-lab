package de.qaware.smartlab.core.exception.handler;

import de.qaware.smartlab.core.exception.data.ConflictException;
import de.qaware.smartlab.core.exception.data.MaximalDurationReachedException;
import de.qaware.smartlab.core.exception.data.MinimalDurationReachedException;
import de.qaware.smartlab.core.exception.data.NotFoundException;
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
    @ExceptionHandler(ConflictException.class)
    public void handleEntityConflictException() {
        logConversion(
                ConflictException.class,
                CONFLICT.value(),
                CONFLICT.name());
    }

    @ResponseStatus(value = NOT_FOUND, reason = "An entity with the specified ID does not exist")
    @ExceptionHandler(NotFoundException.class)
    public void handleEntityNotFoundException() {
        logConversion(
                NotFoundException.class,
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

    @ResponseStatus(value = BAD_REQUEST, reason = "At least one of the specified arguments is not valid")
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException() {
        logConversion(
                IllegalArgumentException.class,
                BAD_REQUEST.value(),
                BAD_REQUEST.name());
    }

    private void logConversion(Class<? extends Exception> exceptionClass, int httpStatusCode, String httpStatusName) {
        log.warn(format(
                "Converted exception \"%s\" to HTTP status code %d %s",
                exceptionClass.getSimpleName(),
                httpStatusCode,
                httpStatusName));
    }
}
