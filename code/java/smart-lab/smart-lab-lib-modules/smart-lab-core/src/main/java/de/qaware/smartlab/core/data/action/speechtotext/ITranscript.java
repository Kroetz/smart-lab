package de.qaware.smartlab.core.data.action.speechtotext;

import de.qaware.smartlab.core.miscellaneous.DurationTimestamp;

import java.util.List;
import java.util.Set;

public interface ITranscript {

    List<ITranscriptParagraph> getParagraphs();
    Set<String> getSpeakers();
    DurationTimestamp getTimestamp();
    String toHumanReadable();
}