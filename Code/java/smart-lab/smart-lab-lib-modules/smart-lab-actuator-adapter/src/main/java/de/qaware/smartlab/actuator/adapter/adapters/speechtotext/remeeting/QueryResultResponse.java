package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.core.data.action.speechtotext.ITextPassagesBuilder;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscriptTextBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
// TODO: Better class name?
public class QueryResultResponse implements ITranscript {

    // TODO: Eliminate string literals

    @JsonProperty("created")
    private String created;

    @JsonProperty("id")
    private String id;

    @JsonProperty("updated")
    private String updated;

    @JsonProperty("results")
    private List<RemeetingResult> results;

    @JsonProperty("status")
    private String status;

    @Override
    public String toHumanReadable(ITranscriptTextBuilder transcriptTextBuilder, ITextPassagesBuilder textPassagesBuilder) {
        // TODO: Build a readable string
        return results.toString();
    }

    @Data
    @NoArgsConstructor
    public static class RemeetingResult {

        @JsonProperty("result_index")
        private String result_index;

        @JsonProperty("results")
        private List<RemeetingSubResult> results;
    }

    @Data
    @NoArgsConstructor
    public static class RemeetingSubResult {

        @JsonProperty("final")
        private boolean final_var;

        @JsonProperty("alternatives")
        private List<Alternative> alternatives;
    }

    @Data
    @NoArgsConstructor
    public static class Alternative {

        @JsonProperty("transcript")
        private String transcript;

        @JsonProperty("transcript_raw")
        private String transcript_raw;

        @JsonProperty("confidence")
        private String confidence;
    }
}
