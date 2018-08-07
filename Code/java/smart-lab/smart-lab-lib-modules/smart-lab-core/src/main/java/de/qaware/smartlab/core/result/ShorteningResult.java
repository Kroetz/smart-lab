package de.qaware.smartlab.core.result;

import de.qaware.smartlab.core.exception.EntityNotFoundException;
import de.qaware.smartlab.core.exception.MinimalDurationReachedException;
import de.qaware.smartlab.core.exception.UnknownErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public enum ShorteningResult {

    SUCCESS(ResponseEntity.ok().build()),
    NOT_FOUND(ResponseEntity.notFound().build()),
    MINIMUM_REACHED(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build()),
    ERROR(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

    private static final Map<HttpStatus, ShorteningResult> SHORTENING_RESULTS_BY_HTTP_STATUS;
    static
    {
        SHORTENING_RESULTS_BY_HTTP_STATUS = new HashMap<>();
        SHORTENING_RESULTS_BY_HTTP_STATUS.put(HttpStatus.OK, ShorteningResult.SUCCESS);
        SHORTENING_RESULTS_BY_HTTP_STATUS.put(HttpStatus.NOT_FOUND, ShorteningResult.NOT_FOUND);
        SHORTENING_RESULTS_BY_HTTP_STATUS.put(HttpStatus.UNPROCESSABLE_ENTITY, ShorteningResult.MINIMUM_REACHED);
        SHORTENING_RESULTS_BY_HTTP_STATUS.put(HttpStatus.INTERNAL_SERVER_ERROR, ShorteningResult.ERROR);
    }

    private ResponseEntity<Void> responseEntity;

    ShorteningResult(ResponseEntity<Void> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity<Void> toResponseEntity() {
        return this.responseEntity;
    }

    public static ShorteningResult fromHttpStatus(HttpStatus httpStatus) {
        ShorteningResult shorteningResult = SHORTENING_RESULTS_BY_HTTP_STATUS.get(httpStatus);
        if(isNull(shorteningResult)) {
            throw new IllegalArgumentException("ShorteningResult cannot be created from the passed HTTP status");
        }
        return shorteningResult;
    }

    public static ShorteningResult fromException(Exception e) {
        if(e instanceof EntityNotFoundException) {
            return NOT_FOUND;
        }
        if(e instanceof MinimalDurationReachedException) {
            return MINIMUM_REACHED;
        }
        if(e instanceof UnknownErrorException) {
            return ERROR;
        }
        return SUCCESS;
    }
}
