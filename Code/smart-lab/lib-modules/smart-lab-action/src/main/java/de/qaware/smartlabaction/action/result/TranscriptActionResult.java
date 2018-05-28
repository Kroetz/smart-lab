package de.qaware.smartlabaction.action.result;

import de.qaware.smartlabaction.action.web.ITranscript;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TranscriptActionResult extends AbstractActionResult<ITranscript> {

    public TranscriptActionResult(ITranscript value) {
        super(value);
    }

    public static TranscriptActionResult of(ITranscript value) {
        return new TranscriptActionResult(value);
    }
}
