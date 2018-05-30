package de.qaware.smartlabcommons.data.action.speechtotext;

import de.qaware.smartlabcommons.miscellaneous.StartedDuration;

import java.util.List;

public interface ITextPassagesBuilder {

    void addTextPassage(
            StartedDuration spokenDuration,
            String speakerName,
            String spokenText);
    List<ITextPassage> getFinishedTextPassages();
}
