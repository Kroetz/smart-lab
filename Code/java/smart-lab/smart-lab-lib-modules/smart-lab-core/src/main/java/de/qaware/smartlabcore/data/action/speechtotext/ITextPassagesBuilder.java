package de.qaware.smartlabcore.data.action.speechtotext;

import de.qaware.smartlabcore.miscellaneous.StartedDuration;

import java.util.List;

public interface ITextPassagesBuilder {

    void addTextPassage(
            StartedDuration spokenDuration,
            String speakerName,
            String spokenText);
    List<ITextPassage> getFinishedTextPassages();
}
