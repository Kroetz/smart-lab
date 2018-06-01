package de.qaware.smartlabcore.result;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public enum StartMeetingResult {

    SUCCESS(ResponseEntity.ok().build());

    private static final Map<HttpStatus, StartMeetingResult> START_MEETING_RESULTS_BY_HTTP_STATUS;
    static
    {
        START_MEETING_RESULTS_BY_HTTP_STATUS = new HashMap<>();
        START_MEETING_RESULTS_BY_HTTP_STATUS.put(HttpStatus.OK, StartMeetingResult.SUCCESS);
    }

    private ResponseEntity<Void> responseEntity;

    StartMeetingResult(ResponseEntity<Void> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity<Void> toResponseEntity() {
        return this.responseEntity;
    }

    public static StartMeetingResult fromHttpStatus(HttpStatus httpStatus) {
        StartMeetingResult startMeetingResult = START_MEETING_RESULTS_BY_HTTP_STATUS.get(httpStatus);
        if(startMeetingResult == null) {
            throw new IllegalArgumentException("StartMeetingResult cannot be created from the passed HTTP status");
        }
        return startMeetingResult;
    }

    public static StartMeetingResult fromException(Exception e) {
        return SUCCESS;
    }
}
