package de.qaware.smartlab.actuator.adapter.adapters.speechtotext;

import de.qaware.smartlab.core.miscellaneous.StartedDuration;

public interface ITextPassage {

    void extendBy(ITextPassage textPassage);
    StartedDuration getSpokenDuration();
    String getSpeakerName();
    String getSpokenText();
}
