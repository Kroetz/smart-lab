package de.qaware.smartlabcore.generic.result;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public enum DeletionResult {

    SUCCESS(ResponseEntity.ok().build()),
    NOT_FOUND(ResponseEntity.notFound().build()),
    ERROR(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

    private static final Map<HttpStatus, DeletionResult> DELETION_RESULTS_BY_HTTP_STATUS;
    static
    {
        DELETION_RESULTS_BY_HTTP_STATUS = new HashMap<>();
        DELETION_RESULTS_BY_HTTP_STATUS.put(HttpStatus.OK, DeletionResult.SUCCESS);
        DELETION_RESULTS_BY_HTTP_STATUS.put(HttpStatus.NOT_FOUND, DeletionResult.NOT_FOUND);
        DELETION_RESULTS_BY_HTTP_STATUS.put(HttpStatus.INTERNAL_SERVER_ERROR, DeletionResult.ERROR);
    }

    private ResponseEntity<Void> responseEntity;

    DeletionResult(ResponseEntity<Void> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity<Void> toResponseEntity() {
        return this.responseEntity;
    }

    public static DeletionResult fromHttpStatus(HttpStatus httpStatus) {
        DeletionResult deletionResult = DELETION_RESULTS_BY_HTTP_STATUS.get(httpStatus);
        if(deletionResult == null) {
            throw new IllegalArgumentException("DeletionResult cannot be created from the passed HTTP status");
        }
        return deletionResult;
    }
}
