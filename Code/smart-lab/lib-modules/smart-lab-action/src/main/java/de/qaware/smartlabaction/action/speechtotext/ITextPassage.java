package de.qaware.smartlabaction.action.speechtotext;

import de.qaware.smartlabcommons.miscellaneous.StartedDuration;

public interface ITextPassage {

    void extendBy(ITextPassage textPassage);
    StartedDuration getSpokenDuration();
    String getSpeakerName();
    String getSpokenText();
}
