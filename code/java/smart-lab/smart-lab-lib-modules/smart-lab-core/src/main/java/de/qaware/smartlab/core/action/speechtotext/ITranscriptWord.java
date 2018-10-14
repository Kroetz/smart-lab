package de.qaware.smartlab.core.action.speechtotext;

import de.qaware.smartlab.core.miscellaneous.DurationTimestamp;

public interface ITranscriptWord {

    String getValue();
    String getSpeaker();
    DurationTimestamp getTimestamp();
    String toHumanReadable();
}
