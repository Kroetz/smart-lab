package de.qaware.smartlabcore.result;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public enum StopMeetingResult {

    SUCCESS(ResponseEntity.ok().build());

    private static final Map<HttpStatus, StopMeetingResult> STOP_MEETING_RESULTS_BY_HTTP_STATUS;
    static
    {
        STOP_MEETING_RESULTS_BY_HTTP_STATUS = new HashMap<>();
        STOP_MEETING_RESULTS_BY_HTTP_STATUS.put(HttpStatus.OK, StopMeetingResult.SUCCESS);
    }

    private ResponseEntity<Void> responseEntity;

    StopMeetingResult(ResponseEntity<Void> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity<Void> toResponseEntity() {
        return this.responseEntity;
    }

    public static StopMeetingResult fromHttpStatus(HttpStatus httpStatus) {
        StopMeetingResult stopMeetingResult = STOP_MEETING_RESULTS_BY_HTTP_STATUS.get(httpStatus);
        if(stopMeetingResult == null) {
            throw new IllegalArgumentException("StopMeetingResult cannot be created from the passed HTTP status");
        }
        return stopMeetingResult;
    }

    public static StopMeetingResult fromException(Exception e) {
        return SUCCESS;
    }
}
