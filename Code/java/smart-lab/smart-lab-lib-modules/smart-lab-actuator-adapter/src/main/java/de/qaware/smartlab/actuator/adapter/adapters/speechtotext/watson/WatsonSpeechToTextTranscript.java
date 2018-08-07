package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.watson;

import com.ibm.watson.developer_cloud.speech_to_text.v1.model.*;
import de.qaware.smartlabcore.data.action.speechtotext.ITextPassage;
import de.qaware.smartlabcore.data.action.speechtotext.ITextPassagesBuilder;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscriptTextBuilder;
import de.qaware.smartlabcore.miscellaneous.StartedDuration;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

@Slf4j
public class WatsonSpeechToTextTranscript implements ITranscript {

    private final SpeechRecognitionResults speechRecognitionResults;

    private WatsonSpeechToTextTranscript(SpeechRecognitionResults speechRecognitionResults) {
        this.speechRecognitionResults = speechRecognitionResults;
    }

    public static WatsonSpeechToTextTranscript of(SpeechRecognitionResults speechRecognitionResults) {
        return new WatsonSpeechToTextTranscript(speechRecognitionResults);
    }

    @Override
    public String toHumanReadable(
            ITranscriptTextBuilder transcriptTextBuilder,
            ITextPassagesBuilder textPassagesBuilder) {
        log.info("Converting Watson speech-to-text transcript into human readable form");
        if(this.speechRecognitionResults.getResults().size() < 1 || isNull(this.speechRecognitionResults.getSpeakerLabels())) {
            log.info("Transcript cannot be converted because it is empty");
            // TODO: String literal
            return "Talk is silver, silence is golden.";
        }
        List<SpeakerLabelsResult> speakerLabelsResults = this.speechRecognitionResults.getSpeakerLabels();
        Map<Timestamp, Long> speakerIdsByTimestamps = speakerLabelsResults.stream().collect(toMap(
                speakerLabelsResult -> Timestamp.of(speakerLabelsResult.getFrom(), speakerLabelsResult.getTo()),
                SpeakerLabelsResult::getSpeaker));
        List<ITextPassage> textPassages = resolveTextPassages(
                textPassagesBuilder,
                this.speechRecognitionResults.getResults(),
                speakerIdsByTimestamps);
        return transcriptTextBuilder.buildText(textPassages);
    }

    private List<ITextPassage> resolveTextPassages(
            ITextPassagesBuilder textPassagesBuilder,
            List<SpeechRecognitionResult> speechRecognitionResults,
            Map<Timestamp, Long> speakerIdsByTimestamps) {
        List<ITextPassage> textPassages = new ArrayList<>();
        for(SpeechRecognitionResult speechRecognitionResult : speechRecognitionResults){
            if(speechRecognitionResult.isFinalResults()) {
                SpeechRecognitionAlternative alternative = getMostConfidentAlternative(speechRecognitionResult);
                textPassages.addAll(resolveTextPassages(textPassagesBuilder, alternative, speakerIdsByTimestamps));
            }
        }
        return textPassages;
    }

    private List<ITextPassage> resolveTextPassages(
            ITextPassagesBuilder textPassagesBuilder,
            SpeechRecognitionAlternative alternative,
            Map<Timestamp, Long> speakerIdsByTimestamps) {
        for(SpeechTimestamp timestamp : alternative.getTimestamps()) {
            Double start = timestamp.getStartTime();
            Double end = timestamp.getEndTime();
            Long speakerId = speakerIdsByTimestamps.get(Timestamp.of(start.floatValue(), end.floatValue()));
            if(isNull(speakerId)) {
                // TODO: Better exception
                throw new RuntimeException();
            }
            textPassagesBuilder.addTextPassage(
                    StartedDuration.ofSeconds(start, end),
                    // TODO: Move string literal to constant
                    "Speaker " + speakerId,
                    timestamp.getWord());
        }
        return textPassagesBuilder.getFinishedTextPassages();
    }

    private SpeechRecognitionAlternative getMostConfidentAlternative(SpeechRecognitionResult speechRecognitionResult) {
        final Comparator<SpeechRecognitionAlternative> comp = Comparator.comparingDouble(SpeechRecognitionAlternative::getConfidence);
        return speechRecognitionResult.getAlternatives().stream().max(comp).orElseThrow(IllegalStateException::new);    // TODO: Better exception
    }

    public static class Timestamp {

        private final Float start;
        private final Float end;

        private Timestamp(Float start, Float end) {
            this.start = start;
            this.end = end;
        }

        public static Timestamp of(Float start, Float end) {
            return new Timestamp(start, end);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Timestamp)) return false;
            Timestamp timestamp = (Timestamp) o;
            return Objects.equals(start, timestamp.start) &&
                    Objects.equals(end, timestamp.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }
}
