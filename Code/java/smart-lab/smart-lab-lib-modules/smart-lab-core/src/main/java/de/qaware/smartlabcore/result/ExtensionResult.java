package de.qaware.smartlabcore.result;

import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MaximalDurationReachedException;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public enum ExtensionResult {

    SUCCESS(ResponseEntity.ok().build()),
    NOT_FOUND(ResponseEntity.notFound().build()),
    CONFLICT(ResponseEntity.status(HttpStatus.CONFLICT).build()),
    MAXIMUM_REACHED_REACHED(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build()),
    ERROR(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

    private static final Map<HttpStatus, ExtensionResult> EXTENSION_RESULTS_BY_HTTP_STATUS;
    static
    {
        EXTENSION_RESULTS_BY_HTTP_STATUS = new HashMap<>();
        EXTENSION_RESULTS_BY_HTTP_STATUS.put(HttpStatus.OK, ExtensionResult.SUCCESS);
        EXTENSION_RESULTS_BY_HTTP_STATUS.put(HttpStatus.NOT_FOUND, ExtensionResult.NOT_FOUND);
        EXTENSION_RESULTS_BY_HTTP_STATUS.put(HttpStatus.CONFLICT, ExtensionResult.CONFLICT);
        EXTENSION_RESULTS_BY_HTTP_STATUS.put(HttpStatus.UNPROCESSABLE_ENTITY, ExtensionResult.MAXIMUM_REACHED_REACHED);
        EXTENSION_RESULTS_BY_HTTP_STATUS.put(HttpStatus.INTERNAL_SERVER_ERROR, ExtensionResult.ERROR);
    }

    private ResponseEntity<Void> responseEntity;

    ExtensionResult(ResponseEntity<Void> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity<Void> toResponseEntity() {
        return this.responseEntity;
    }

    public static ExtensionResult fromHttpStatus(HttpStatus httpStatus) {
        ExtensionResult extensionResult = EXTENSION_RESULTS_BY_HTTP_STATUS.get(httpStatus);
        if(isNull(extensionResult)) {
            throw new IllegalArgumentException("ExtensionResult cannot be created from the passed HTTP status");
        }
        return extensionResult;
    }

    public static ExtensionResult fromException(Exception e) {
        if(e instanceof EntityNotFoundException) {
            return NOT_FOUND;
        }
        if(e instanceof EntityConflictException) {
            return CONFLICT;
        }
        if(e instanceof MaximalDurationReachedException) {
            return MAXIMUM_REACHED_REACHED;
        }
        if(e instanceof UnknownErrorException) {
            return ERROR;
        }
        return SUCCESS;
    }
}
