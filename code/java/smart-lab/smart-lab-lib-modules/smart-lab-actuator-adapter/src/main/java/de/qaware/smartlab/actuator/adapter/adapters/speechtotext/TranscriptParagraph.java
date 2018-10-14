package de.qaware.smartlab.actuator.adapter.adapters.speechtotext;

import de.qaware.smartlab.core.action.speechtotext.ITranscriptParagraph;
import de.qaware.smartlab.core.action.speechtotext.ITranscriptWord;
import de.qaware.smartlab.core.miscellaneous.DurationTimestamp;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static de.qaware.smartlab.core.util.StringUtils.*;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Slf4j
public class TranscriptParagraph implements ITranscriptParagraph {

    private final List<ITranscriptWord> words;
    private final String speaker;
    private final DurationTimestamp timestamp;

    private TranscriptParagraph(List<ITranscriptWord> words) {
        this.words = words;
        String speaker = words.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("There must be a speaker for a transcript paragraph"))
                .getSpeaker();
        if(!words.stream().map(ITranscriptWord::getSpeaker).allMatch(s -> s.equals(speaker))) {
            throw new IllegalStateException("All words in a transcript paragraph must have the same speaker");
        }
        this.speaker = speaker;
        this.timestamp = DurationTimestamp.enclosingTimestamp(words.stream()
                .map(ITranscriptWord::getTimestamp)
                .collect(toSet()));
    }

    @Override
    public List<ITranscriptWord> getWords() {
        return words;
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(this.timestamp.toString())
                .append(SPACE)
                .append(this.speaker)
                .append(COLON)
                .append(SPACE);
        Iterator<ITranscriptWord> iterator = this.words.iterator();
        while(iterator.hasNext()) {
            ITranscriptWord word = iterator.next();
            stringBuilder.append(word.toHumanReadable());
            if(iterator.hasNext()) stringBuilder.append(SPACE);
        }
        return stringBuilder.toString();
    }

    public static List<TranscriptParagraph> of(List<ITranscriptWord> words) {
        List<TranscriptParagraph> paragraphs = new ArrayList<>();
        List<ITranscriptWord> currentParagraphWords = new ArrayList<>();
        String currentSpeaker = null;
        for(ITranscriptWord word : words) {
            if(!word.getSpeaker().equals(currentSpeaker)) {
                if(nonNull(currentSpeaker)) paragraphs.add(new TranscriptParagraph(currentParagraphWords));
                currentParagraphWords = new ArrayList<>();
                currentSpeaker = word.getSpeaker();
            }
            currentParagraphWords.add(word);
        }
        paragraphs.add(new TranscriptParagraph(currentParagraphWords));
        return paragraphs;
    }
}
