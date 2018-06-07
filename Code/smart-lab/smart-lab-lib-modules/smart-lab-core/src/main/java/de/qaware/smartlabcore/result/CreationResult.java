package de.qaware.smartlabcore.result;

import de.qaware.smartlabcore.exception.MeetingConflictException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public enum CreationResult {

    SUCCESS(ResponseEntity.ok().build()),
    CONFLICT(ResponseEntity.status(HttpStatus.CONFLICT).build()),
    ERROR(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

    private static final Map<HttpStatus, CreationResult> CREATION_RESULTS_BY_HTTP_STATUS;
    static
    {
        CREATION_RESULTS_BY_HTTP_STATUS = new HashMap<>();
        CREATION_RESULTS_BY_HTTP_STATUS.put(HttpStatus.OK, CreationResult.SUCCESS);
        CREATION_RESULTS_BY_HTTP_STATUS.put(HttpStatus.CONFLICT, CreationResult.CONFLICT);
        CREATION_RESULTS_BY_HTTP_STATUS.put(HttpStatus.INTERNAL_SERVER_ERROR, CreationResult.ERROR);
    }

    private ResponseEntity<Void> responseEntity;

    CreationResult(ResponseEntity<Void> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity<Void> toResponseEntity() {
        return this.responseEntity;
    }

    public static CreationResult fromHttpStatus(HttpStatus httpStatus) {
        CreationResult creationResult = CREATION_RESULTS_BY_HTTP_STATUS.get(httpStatus);
        if(creationResult == null) {
            throw new IllegalArgumentException("CreationResult cannot be created from the passed HTTP status");
        }
        return creationResult;
    }

    public static CreationResult fromException(Exception e) {
        if(e instanceof MeetingConflictException) {
            return CONFLICT;
        }
        if(e instanceof UnknownErrorException) {
            return ERROR;
        }
        return SUCCESS;
    }
}
