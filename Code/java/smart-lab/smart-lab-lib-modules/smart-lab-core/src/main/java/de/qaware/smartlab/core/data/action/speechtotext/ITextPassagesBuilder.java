package de.qaware.smartlab.core.data.action.speechtotext;

import de.qaware.smartlab.core.miscellaneous.StartedDuration;

import java.util.List;

public interface ITextPassagesBuilder {

    void addTextPassage(
            StartedDuration spokenDuration,
            String speakerName,
            String spokenText);
    List<ITextPassage> getFinishedTextPassages();
}
