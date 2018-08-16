package de.qaware.smartlab.core.data.action.speechtotext;

import de.qaware.smartlab.core.miscellaneous.DurationTimestamp;

public interface ITranscriptWord {

    String getValue();
    String getSpeaker();
    DurationTimestamp getTimestamp();
    String toHumanReadable();
}
