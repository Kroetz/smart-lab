package de.qaware.smartlabcommons.api.external.watson.speechtotext;

import com.ibm.watson.developer_cloud.speech_to_text.v1.model.*;
import de.qaware.smartlabcommons.data.action.web.*;
import de.qaware.smartlabcommons.miscellaneous.StartedDuration;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

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
    public String toHumanReadable(ITranscriptTextBuilder transcriptTextBuilder) {
        List<SpeakerLabelsResult> speakerLabelsResults = this.speechRecognitionResults.getSpeakerLabels();
        Map<Timestamp, Long> speakerIdsByTimestamps = speakerLabelsResults.stream().collect(Collectors.toMap(
                speakerLabelsResult -> Timestamp.of(speakerLabelsResult.getFrom(), speakerLabelsResult.getTo()),
                SpeakerLabelsResult::getSpeaker));
        List<ITextPassage> textPassages = resolveTextPassages(this.speechRecognitionResults.getResults(), speakerIdsByTimestamps);
        return transcriptTextBuilder.buildText(textPassages);
    }

    private List<ITextPassage> resolveTextPassages(
            List<SpeechRecognitionResult> speechRecognitionResults,
            Map<Timestamp, Long> speakerIdsByTimestamps) {
        List<ITextPassage> textPassages = new ArrayList<>();
        for(SpeechRecognitionResult speechRecognitionResult : speechRecognitionResults){
            if(speechRecognitionResult.isFinalResults()) {
                SpeechRecognitionAlternative alternative = getMostConfidentAlternative(speechRecognitionResult);
                textPassages.addAll(resolveTextPassages(alternative, speakerIdsByTimestamps));
            }
        }
        return textPassages;
    }

    private List<ITextPassage> resolveTextPassages(
            SpeechRecognitionAlternative alternative,
            Map<Timestamp, Long> speakerIdsByTimestamps) {
        TextPassagesBuilder textPassagesBuilder = TextPassagesBuilder.newInstance();
        for(SpeechTimestamp timestamp : alternative.getTimestamps()) {
            Double start = timestamp.getStartTime();
            Double end = timestamp.getEndTime();
            Long speakerId = speakerIdsByTimestamps.get(Timestamp.of(start.floatValue(), end.floatValue()));
            if(speakerId == null) {
                // TODO: Better exception
                throw new RuntimeException();
            }
            textPassagesBuilder.addTextPassage(TextPassage.of(
                    StartedDuration.ofSeconds(start, end),
                    // TODO: Move string literal to constant
                    "Speaker " + speakerId,
                    timestamp.getWord()));
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
