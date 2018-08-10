package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
// TODO: Better class name?
public class QueryResultResponse implements ITranscript {

    private static final String FIELD_NAME_CREATED = "created";
    private static final String FIELD_NAME_ID = "id";
    private static final String FIELD_NAME_UPDATED = "updated";
    private static final String FIELD_NAME_RESULTS = "results";
    private static final String FIELD_NAME_STATUS = "status";

    public static final String STATUS_WAITING = "waiting";
    public static final String STATUS_PROCESSING = "processing";
    public static final String STATUS_COMPLETED = "completed";

    @JsonProperty(FIELD_NAME_CREATED)
    private String created;

    @JsonProperty(FIELD_NAME_ID)
    private String id;

    @JsonProperty(FIELD_NAME_UPDATED)
    private String updated;

    @JsonProperty(FIELD_NAME_RESULTS)
    private List<RemeetingResult> results;

    @JsonProperty(FIELD_NAME_STATUS)
    private String status;

    @Override
    public String toHumanReadable() {
        // TODO: Use TranscriptTextBuilder to build a human readable string
        return results.toString();
    }

    @Data
    @NoArgsConstructor
    public static class RemeetingResult {

        private static final String FIELD_NAME_RESULT_INDEX = "result_index";
        private static final String FIELD_NAME_RESULTS = "results";

        @JsonProperty(FIELD_NAME_RESULT_INDEX)
        private String result_index;

        @JsonProperty(FIELD_NAME_RESULTS)
        private List<RemeetingSubResult> results;
    }

    @Data
    @NoArgsConstructor
    public static class RemeetingSubResult {

        private static final String FIELD_NAME_FINAL = "final";
        private static final String FIELD_NAME_ALTERNATIVES = "alternatives";

        @JsonProperty(FIELD_NAME_FINAL)
        private boolean final_var;

        @JsonProperty(FIELD_NAME_ALTERNATIVES)
        private List<Alternative> alternatives;
    }

    @Data
    @NoArgsConstructor
    public static class Alternative {

        private static final String FIELD_NAME_TRANSCRIPT = "transcript";
        private static final String FIELD_NAME_TRANSCRIPT_RAW = "transcript_raw";
        private static final String FIELD_NAME_CONFIDENCE = "confidence";

        @JsonProperty(FIELD_NAME_TRANSCRIPT)
        private String transcript;

        @JsonProperty(FIELD_NAME_TRANSCRIPT_RAW)
        private String transcript_raw;

        @JsonProperty(FIELD_NAME_CONFIDENCE)
        private String confidence;
    }
}
