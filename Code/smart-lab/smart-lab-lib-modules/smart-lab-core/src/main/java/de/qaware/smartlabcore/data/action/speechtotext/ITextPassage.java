package de.qaware.smartlabcore.data.action.speechtotext;

import de.qaware.smartlabcore.miscellaneous.StartedDuration;

public interface ITextPassage {

    void extendBy(ITextPassage textPassage);
    StartedDuration getSpokenDuration();
    String getSpeakerName();
    String getSpokenText();
}
