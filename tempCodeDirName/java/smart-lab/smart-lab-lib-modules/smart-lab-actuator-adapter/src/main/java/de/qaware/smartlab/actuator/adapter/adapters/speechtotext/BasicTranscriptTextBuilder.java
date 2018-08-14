package de.qaware.smartlab.actuator.adapter.adapters.speechtotext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BasicTranscriptTextBuilder implements ITranscriptTextBuilder {

    @Override
    public String buildText(List<ITextPassage> textPassages) {
        StringBuilder stringBuilder = new StringBuilder();
        addTextPassages(stringBuilder, textPassages);
        return stringBuilder.toString();
    }

    @Override
    public void addTextPassages(StringBuilder stringBuilder, List<ITextPassage> textPassages) {
        textPassages.forEach(textPassage -> addTextPassage(stringBuilder, textPassage));
    }

    @Override
    public void addTextPassage(StringBuilder stringBuilder, ITextPassage textPassage) {
        stringBuilder
                .append(textPassage.getSpokenDuration().toString())
                .append(" ")
                .append(textPassage.getSpeakerName())
                .append(": ")
                .append(textPassage.getSpokenText())
                .append("\n\n");
    }
}

