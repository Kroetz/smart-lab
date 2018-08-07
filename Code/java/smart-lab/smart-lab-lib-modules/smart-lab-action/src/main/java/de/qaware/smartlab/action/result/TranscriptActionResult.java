package de.qaware.smartlab.action.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import lombok.*;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TranscriptActionResult extends AbstractActionResult<ITranscript> {

    private TranscriptActionResult(ITranscript value) {
        super(value);
    }

    @JsonCreator
    public static TranscriptActionResult of(@JsonProperty(VALUE_FIELD_NAME) ITranscript value) {
        return new TranscriptActionResult(value);
    }
}
