package de.qaware.smartlabaction.action.submittable.speechtotext;

import de.qaware.smartlabcore.data.action.speechtotext.ITextPassage;
import de.qaware.smartlabcore.data.action.speechtotext.ITextPassagesBuilder;
import de.qaware.smartlabcore.miscellaneous.StartedDuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TextPassagesBuilder implements ITextPassagesBuilder {

    private final List<ITextPassage> finishedTextPassages;
    private ITextPassage unfinishedTextPassage;

    private TextPassagesBuilder() {
        this.finishedTextPassages = new ArrayList<>();
    }

    public static TextPassagesBuilder newInstance() {
        return new TextPassagesBuilder();
    }

    public void addTextPassage(
            StartedDuration spokenDuration,
            String speakerName,
            String spokenText) {
        ITextPassage textPassage = TextPassage.of(spokenDuration, speakerName, spokenText);
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
