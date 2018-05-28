package de.qaware.smartlabaction.action.web;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TextPassagesBuilder {

    private final List<ITextPassage> finishedTextPassages;
    private ITextPassage unfinishedTextPassage;

    private TextPassagesBuilder() {
        this.finishedTextPassages = new ArrayList<>();
    }

    public static TextPassagesBuilder newInstance() {
        return new TextPassagesBuilder();
    }

    public void addTextPassage(ITextPassage textPassage) {
        if(unfinishedTextPassage == null) {
            this.unfinishedTextPassage = TextPassage.of(
                    textPassage.getSpokenDuration(),
                    textPassage.getSpeakerName(),
                    textPassage.getSpokenText());
        }
        else if(!unfinishedTextPassage.getSpeakerName().equals(textPassage.getSpeakerName())) {
            finishUnfinishedTextPassage();
            this.unfinishedTextPassage = TextPassage.of(
                    textPassage.getSpokenDuration(),
                    textPassage.getSpeakerName(),
                    textPassage.getSpokenText());
        }
        else {
            this.unfinishedTextPassage.extendBy(textPassage);
        }
    }

    public List<ITextPassage> getFinishedTextPassages() {
        finishUnfinishedTextPassage();
        return this.finishedTextPassages;
    }

    private void finishUnfinishedTextPassage() {
        if(this.unfinishedTextPassage != null) {
            this.finishedTextPassages.add(this.unfinishedTextPassage);
            this.unfinishedTextPassage = null;
        }
    }
}
