package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.watson;

import com.ibm.watson.developer_cloud.speech_to_text.v1.model.*;
import de.qaware.smartlab.actuator.adapter.adapters.speechtotext.AbstractSpeechToTextTranscript;
import de.qaware.smartlab.actuator.adapter.adapters.speechtotext.TranscriptParagraph;
import de.qaware.smartlab.actuator.adapter.adapters.speechtotext.TranscriptWord;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscriptParagraph;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscriptWord;
import de.qaware.smartlab.core.miscellaneous.DurationTimestamp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Slf4j
public class WatsonSpeechToTextTranscript extends AbstractSpeechToTextTranscript {

    private WatsonSpeechToTextTranscript(
            Map<DurationTimestamp, Long> speakerIdsByTimestamp,
            List<ITranscriptParagraph> paragraphs,
            Set<String> speakers,
            DurationTimestamp timestamp) {
        super(
                speakerIdsByTimestamp,
                paragraphs,
                speakers,
                timestamp);
    }

    @Component
    @Slf4j
    public static class Factory {

        private static final String SPEAKER_NAME_PREFIX = "Speaker";

        public WatsonSpeechToTextTranscript of(SpeechRecognitionResults speechRecognitionResults) {
            Map<DurationTimestamp, Long> speakerIdsByTimestamp = extractSpeakerIdsByTimestamp(speechRecognitionResults);
            List<ITranscriptParagraph> paragraphs = extractParagraphs(speechRecognitionResults, speakerIdsByTimestamp);
            Set<String> speakers = extractSpeakers(paragraphs);
            DurationTimestamp timestamp = extractTimestamp(paragraphs);
            return new WatsonSpeechToTextTranscript(
                    speakerIdsByTimestamp,
                    paragraphs,
                    speakers,
                    timestamp);
        }

        private Map<DurationTimestamp, Long> extractSpeakerIdsByTimestamp(SpeechRecognitionResults speechRecognitionResults) {
            List<SpeakerLabelsResult> speakerLabels = speechRecognitionResults.getSpeakerLabels();
            if(nonNull(speakerLabels)) {
                return speakerLabels.stream()
                        .collect(toMap(
                                speakerLabelsResult -> DurationTimestamp.ofSeconds(speakerLabelsResult.getFrom(), speakerLabelsResult.getTo()),
                                SpeakerLabelsResult::getSpeaker));
            }
            return new HashMap<>();
        }

        private List<ITranscriptParagraph> extractParagraphs(
                SpeechRecognitionResults speechRecognitionResults,
                Map<DurationTimestamp, Long> speakerIdsByTimestamp) {
            List<ITranscriptParagraph> paragraphs = new ArrayList<>();
            List<SpeechRecognitionResult> results = speechRecognitionResults.getResults();
            for(SpeechRecognitionResult result : results){
                paragraphs.addAll(extractParagraphs(result, speakerIdsByTimestamp));
            }
            return paragraphs;
        }

        private List<ITranscriptParagraph> extractParagraphs(
                SpeechRecognitionResult speechRecognitionResult,
                Map<DurationTimestamp, Long> speakerIdsByTimestamp) {
            List<ITranscriptParagraph> paragraphs = new ArrayList<>();
            if(speechRecognitionResult.isFinalResults()) {
                SpeechRecognitionAlternative alternative = getMostConfidentAlternative(speechRecognitionResult);
                paragraphs.addAll(extractParagraphs(alternative, speakerIdsByTimestamp));
            }
            return paragraphs;
        }

        private List<ITranscriptParagraph> extractParagraphs(
                SpeechRecognitionAlternative speechRecognitionAlternative,
                Map<DurationTimestamp, Long> speakerIdsByTimestamp) {
            List<ITranscriptWord> words = new ArrayList<>();
            for(SpeechTimestamp timestamp : speechRecognitionAlternative.getTimestamps()) {
                Double start = timestamp.getStartTime();
                Double end = timestamp.getEndTime();
                Long speakerId = speakerIdsByTimestamp.get(
                        DurationTimestamp.ofSeconds(start.floatValue(), end.floatValue()));
                if(isNull(speakerId)) throw new NullPointerException("The speaker ID must not be null");
                words.add(TranscriptWord.of(
                        timestamp.getWord(),
                        format("%s %d", SPEAKER_NAME_PREFIX, speakerId),
                        DurationTimestamp.ofSeconds(timestamp.getStartTime(), timestamp.getEndTime())));
            }
            return new ArrayList<>(TranscriptParagraph.of(words));
        }

        private SpeechRecognitionAlternative getMostConfidentAlternative(SpeechRecognitionResult speechRecognitionResult) {
            final Comparator<SpeechRecognitionAlternative> comparator =
                    Comparator.comparingDouble(SpeechRecognitionAlternative::getConfidence);
            return speechRecognitionResult.getAlternatives().stream()
                    .max(comparator)
                    .orElseThrow(() -> {
                        String errorMessage = "There must be a most confident alternative";
                        log.error(errorMessage);
                        return new IllegalStateException(errorMessage);
                    });
        }

        private Set<String> extractSpeakers(List<ITranscriptParagraph> paragraphs) {
            return paragraphs.stream()
                    .map(ITranscriptParagraph::getSpeaker)
                    .collect(toSet());

        }

        private DurationTimestamp extractTimestamp(List<ITranscriptParagraph> paragraphs) {
            return DurationTimestamp.enclosingTimestamp(paragraphs.stream()
                    .map(ITranscriptParagraph::getTimestamp)
                    .collect(toSet()));
        }
    }
}
