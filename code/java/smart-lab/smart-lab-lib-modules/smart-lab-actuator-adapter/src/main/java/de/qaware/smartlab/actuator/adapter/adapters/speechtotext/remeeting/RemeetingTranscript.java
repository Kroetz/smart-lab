package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting;

import de.qaware.smartlab.actuator.adapter.adapters.speechtotext.AbstractSpeechToTextTranscript;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscriptParagraph;
import de.qaware.smartlab.core.miscellaneous.DurationTimestamp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class RemeetingTranscript extends AbstractSpeechToTextTranscript {

    private final JobStatusResponse jobStatusResponse;

    private RemeetingTranscript(
            JobStatusResponse jobStatusResponse,
            Map<DurationTimestamp, Long> speakerIdsByTimestamp,
            List<ITranscriptParagraph> paragraphs,
            Set<String> speakers,
            DurationTimestamp timestamp) {
        super(
                speakerIdsByTimestamp,
                paragraphs,
                speakers,
                timestamp);
        this.jobStatusResponse = jobStatusResponse;
    }

    @Override
    public String toHumanReadable() {
        // TODO: Use TranscriptTextBuilder to build a human readable string
        return this.jobStatusResponse.getResults().toString();
    }

    @Component
    @Slf4j
    public static class Factory {

        public RemeetingTranscript of(JobStatusResponse jobStatusResponse) {
            Map<DurationTimestamp, Long> speakerIdsByTimestamp = extractSpeakerIdsByTimestamp(jobStatusResponse);
            List<ITranscriptParagraph> paragraphs = extractParagraphs(jobStatusResponse);
            Set<String> speakers = extractSpeakers(jobStatusResponse);
            DurationTimestamp timestamp = extractTimestamp(jobStatusResponse);
            return new RemeetingTranscript(
                    jobStatusResponse,
                    speakerIdsByTimestamp,
                    paragraphs,
                    speakers,
                    timestamp);
        }

        private static Map<DurationTimestamp, Long> extractSpeakerIdsByTimestamp(JobStatusResponse jobStatusResponse) {
            // TODO: Extract speaker IDs by timestamp from the response
            return null;
        }

        private static List<ITranscriptParagraph> extractParagraphs(JobStatusResponse jobStatusResponse) {
            // TODO: Extract paragraphs from the response
            return null;
        }

        private static Set<String> extractSpeakers(JobStatusResponse jobStatusResponse) {
            // TODO: Extract speakers from the response
            return null;
        }

        private static DurationTimestamp extractTimestamp(JobStatusResponse jobStatusResponse) {
            // TODO: Extract timestamp from the response
            return null;
        }
    }
}
