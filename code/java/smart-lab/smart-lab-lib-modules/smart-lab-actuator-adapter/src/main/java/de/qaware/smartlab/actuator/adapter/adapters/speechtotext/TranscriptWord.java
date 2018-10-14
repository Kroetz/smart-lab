package de.qaware.smartlab.actuator.adapter.adapters.speechtotext;

import de.qaware.smartlab.core.action.speechtotext.ITranscriptWord;
import de.qaware.smartlab.core.miscellaneous.DurationTimestamp;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TranscriptWord implements ITranscriptWord {

    private final String value;
    private final String speaker;
    private final DurationTimestamp timestamp;

    private TranscriptWord(String value, String speaker, DurationTimestamp timestamp) {
        this.value = value;
        this.speaker = speaker;
        this.timestamp = timestamp;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getSpeaker() {
        return speaker;
    }

    @Override
    public DurationTimestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String toHumanReadable() {
        return this.value;
    }

    public static TranscriptWord of(String value, String speaker, DurationTimestamp timestamp) {
        return new TranscriptWord(value, speaker, timestamp);
    }
}
