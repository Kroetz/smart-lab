package de.qaware.smartlab.core.data.action.speechtotext;

import de.qaware.smartlab.core.miscellaneous.DurationTimestamp;

import java.util.List;

public interface ITranscriptParagraph {

    List<ITranscriptWord> getWords();
    String getSpeaker();
    DurationTimestamp getTimestamp();
    String toHumanReadable();
}
