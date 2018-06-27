package de.qaware.smartlabcore.result;

import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MeetingConflictException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public enum ShiftResult {

    SUCCESS(ResponseEntity.ok().build()),
    NOT_FOUND(ResponseEntity.notFound().build()),
    CONFLICT(ResponseEntity.status(HttpStatus.CONFLICT).build()),
    ERROR(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

    private static final Map<HttpStatus, ShiftResult> SHIFT_RESULTS_BY_HTTP_STATUS;
    static
    {
        SHIFT_RESULTS_BY_HTTP_STATUS = new HashMap<>();
        SHIFT_RESULTS_BY_HTTP_STATUS.put(HttpStatus.OK, ShiftResult.SUCCESS);
        SHIFT_RESULTS_BY_HTTP_STATUS.put(HttpStatus.NOT_FOUND, ShiftResult.NOT_FOUND);
        SHIFT_RESULTS_BY_HTTP_STATUS.put(HttpStatus.CONFLICT, ShiftResult.CONFLICT);
        SHIFT_RESULTS_BY_HTTP_STATUS.put(HttpStatus.INTERNAL_SERVER_ERROR, ShiftResult.ERROR);
    }

    private ResponseEntity<Void> responseEntity;

    ShiftResult(ResponseEntity<Void> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity<Void> toResponseEntity() {
        return this.responseEntity;
    }

    public static ShiftResult fromHttpStatus(HttpStatus httpStatus) {
        ShiftResult shiftResult = SHIFT_RESULTS_BY_HTTP_STATUS.get(httpStatus);
        if(isNull(shiftResult)) {
            throw new IllegalArgumentException("ShiftResult cannot be created from the passed HTTP status");
        }
        return shiftResult;
    }

    public static ShiftResult fromException(Exception e) {
        if(e instanceof EntityNotFoundException) {
            return NOT_FOUND;
        }
        if(e instanceof MeetingConflictException) {
            return CONFLICT;
        }
        if(e instanceof UnknownErrorException) {
            return ERROR;
        }
        return SUCCESS;
    }
}
