package de.qaware.smartlabcore.generic.result;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public enum CleanUpMeetingResult {

    SUCCESS(ResponseEntity.ok().build());

    private static final Map<HttpStatus, CleanUpMeetingResult> CLEAN_UP_MEETING_RESULTS_BY_HTTP_STATUS;
    static
    {
        CLEAN_UP_MEETING_RESULTS_BY_HTTP_STATUS = new HashMap<>();
        CLEAN_UP_MEETING_RESULTS_BY_HTTP_STATUS.put(HttpStatus.OK, CleanUpMeetingResult.SUCCESS);
    }

    private ResponseEntity<Void> responseEntity;

    CleanUpMeetingResult(ResponseEntity<Void> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity<Void> toResponseEntity() {
        return this.responseEntity;
    }

    public static CleanUpMeetingResult fromHttpStatus(HttpStatus httpStatus) {
        val cleanUpMeetingResult = CLEAN_UP_MEETING_RESULTS_BY_HTTP_STATUS.get(httpStatus);
        if(cleanUpMeetingResult == null) {
            throw new IllegalArgumentException("CleanUpMeetingResult cannot be created from the passed HTTP status");
        }
        return cleanUpMeetingResult;
    }
}
