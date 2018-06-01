package de.qaware.smartlabcore.result;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public enum SetUpMeetingResult {

    SUCCESS(ResponseEntity.ok().build());

    private static final Map<HttpStatus, SetUpMeetingResult> SET_UP_MEETING_RESULTS_BY_HTTP_STATUS;
    static
    {
        SET_UP_MEETING_RESULTS_BY_HTTP_STATUS = new HashMap<>();
        SET_UP_MEETING_RESULTS_BY_HTTP_STATUS.put(HttpStatus.OK, SetUpMeetingResult.SUCCESS);
    }

    private ResponseEntity<Void> responseEntity;

    SetUpMeetingResult(ResponseEntity<Void> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity<Void> toResponseEntity() {
        return this.responseEntity;
    }

    public static SetUpMeetingResult fromHttpStatus(HttpStatus httpStatus) {
        SetUpMeetingResult setUpMeetingResult = SET_UP_MEETING_RESULTS_BY_HTTP_STATUS.get(httpStatus);
        if(setUpMeetingResult == null) {
            throw new IllegalArgumentException("SetUpMeetingResult cannot be created from the passed HTTP status");
        }
        return setUpMeetingResult;
    }

    public static SetUpMeetingResult fromException(Exception e) {
        return SUCCESS;
    }
}
