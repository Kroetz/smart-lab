package de.qaware.smartlabaction.action.submittable.speechtotext;

import de.qaware.smartlabcore.data.action.speechtotext.ITextPassage;
import de.qaware.smartlabcore.miscellaneous.StartedDuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextPassage implements ITextPassage {

    private StartedDuration spokenDuration;
    private final String speakerName;
    private final StringBuilder spokenTextBuilder;

    private TextPassage(StartedDuration spokenDuration, String speakerName, String spokenText) {
        this.spokenDuration = spokenDuration;
        this.speakerName = speakerName;
        this.spokenTextBuilder = new StringBuilder();
        this.spokenTextBuilder.append(spokenText);
    }

    public static TextPassage of(StartedDuration spokenDuration, String speakerName, String spokenText) {
        return new TextPassage(spokenDuration, speakerName, spokenText);
    }

    public void extendBy(ITextPassage textPassage) {
        if(!this.speakerName.equals(textPassage.getSpeakerName())) {
            // TODO: better exception
            throw new RuntimeException("speaker names muss be equal");
        }
        appendText(textPassage.getSpokenText());
        extendTo(textPassage.getSpokenDuration().getEndInMillis());
    }

    private void appendText(String textToAppend) {
        this.spokenTextBuilder.append(" ").append(textToAppend);
    }

    private void extendTo(long newEndInMillis) {
        this.spokenDuration = StartedDuration.ofMillis(this.spokenDuration.getStartInMillis(), newEndInMillis);
    }

    public StartedDuration getSpokenDuration() {
        return spokenDuration;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public String getSpokenText() {
        return this.spokenTextBuilder.toString();
    }
}
