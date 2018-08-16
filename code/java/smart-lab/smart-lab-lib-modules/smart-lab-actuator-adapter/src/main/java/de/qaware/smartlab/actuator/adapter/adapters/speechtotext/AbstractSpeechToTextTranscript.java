package de.qaware.smartlab.actuator.adapter.adapters.speechtotext;

import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscriptParagraph;
import de.qaware.smartlab.core.miscellaneous.DurationTimestamp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.qaware.smartlab.core.util.StringUtils.NEW_LINE;

@Slf4j
public abstract class AbstractSpeechToTextTranscript implements ITranscript {

    private static final String EMPTY_TRANSCRIPT_FILLER = "Talk is silver, silence is golden.";

    protected final Map<DurationTimestamp, Long> speakerIdsByTimestamp;
    protected final List<ITranscriptParagraph> paragraphs;
    protected final Set<String> speakers;
    protected final DurationTimestamp timestamp;

    protected AbstractSpeechToTextTranscript(
            Map<DurationTimestamp, Long> speakerIdsByTimestamp,
            List<ITranscriptParagraph> paragraphs,
            Set<String> speakers,
            DurationTimestamp timestamp) {
        this.speakerIdsByTimestamp = speakerIdsByTimestamp;
        this.paragraphs = paragraphs;
        this.speakers = speakers;
        this.timestamp = timestamp;
    }

    @Override
    public List<ITranscriptParagraph> getParagraphs() {
        return this.paragraphs;
    }

    @Override
    public Set<String> getSpeakers() {
        return this.speakers;
    }

    @Override
    public DurationTimestamp getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toHumanReadable() {
        log.info("Converting speech-to-text transcript into human readable form");
        StringBuilder stringBuilder = new StringBuilder();
        if(this.paragraphs.isEmpty()) {
            log.info("Transcript cannot be converted because it is empty");
            stringBuilder.append(EMPTY_TRANSCRIPT_FILLER);
        }
        for(ITranscriptParagraph paragraph : this.paragraphs) {
            stringBuilder.append(paragraph.toHumanReadable());
            stringBuilder
                    .append(NEW_LINE)
                    .append(NEW_LINE);
        }
        return stringBuilder.toString();
    }
}
