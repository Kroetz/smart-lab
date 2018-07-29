package de.qaware.smartlabaction.action.result;

import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import lombok.*;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TranscriptActionResult extends AbstractActionResult<ITranscript> {

    private TranscriptActionResult(ITranscript value) {
        super(value);
    }

    public static TranscriptActionResult of(ITranscript value) {
        return new TranscriptActionResult(value);
    }
}
