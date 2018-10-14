package de.qaware.smartlab.action.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.core.action.speechtotext.ITranscript;
import lombok.*;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TranscriptActionResult extends AbstractActionResult<ITranscript> {

    private TranscriptActionResult(ITranscript value) {
        super(value);
    }

    @JsonCreator
    public static TranscriptActionResult of(@JsonProperty(FIELD_NAME_VALUE) ITranscript value) {
        return new TranscriptActionResult(value);
    }
}
