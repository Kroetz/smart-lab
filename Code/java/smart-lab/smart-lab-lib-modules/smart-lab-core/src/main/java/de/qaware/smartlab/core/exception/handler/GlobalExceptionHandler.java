package de.qaware.smartlab.core.exception.handler;

import de.qaware.smartlab.core.exception.EntityConflictException;
import de.qaware.smartlab.core.exception.EntityNotFoundException;
import de.qaware.smartlab.core.exception.MinimalDurationReachedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "An entity with the specified ID already exists")
    @ExceptionHandler(EntityConflictException.class)
    public void handleEntityConflictException(){
        logConversion(
                EntityConflictException.class,
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "An entity with the specified ID does not exist")
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(){
        logConversion(
                EntityNotFoundException.class,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name());
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "The specified event already reached its minimal duration")
    @ExceptionHandler(MinimalDurationReachedException.class)
    public void handleMinimalDurationReachedException(){
        logConversion(
                MinimalDurationReachedException.class,
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.name());
    }

    private void logConversion(Class<? extends Exception> exceptionClass, int httpStatusCode, String httpStatusName) {
        log.warn(format(
                "Converted exception \"%s\" to HTTP status code %d %s",
                exceptionClass.getSimpleName(),
                httpStatusCode,
                httpStatusName));
    }
}
