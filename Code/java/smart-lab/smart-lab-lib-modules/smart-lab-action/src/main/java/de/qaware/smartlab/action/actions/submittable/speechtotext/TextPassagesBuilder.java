package de.qaware.smartlab.action.actions.submittable.speechtotext;

import de.qaware.smartlab.core.data.action.speechtotext.ITextPassage;
import de.qaware.smartlab.core.data.action.speechtotext.ITextPassagesBuilder;
import de.qaware.smartlab.core.miscellaneous.StartedDuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
        if(isNull(unfinishedTextPassage)) {
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
        if(nonNull(this.unfinishedTextPassage)) {
            this.finishedTextPassages.add(this.unfinishedTextPassage);
            this.unfinishedTextPassage = null;
        }
    }
}
