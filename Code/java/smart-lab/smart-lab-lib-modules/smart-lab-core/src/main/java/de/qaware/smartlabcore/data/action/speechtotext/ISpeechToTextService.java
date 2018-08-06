package de.qaware.smartlabcore.data.action.speechtotext;

import de.qaware.smartlabcore.exception.ServiceFailedException;
import de.qaware.smartlabcore.miscellaneous.Language;

import java.nio.file.Path;

public interface ISpeechToTextService {

    String getServiceId();
    ITranscript speechToText(Path audioFile, Language spokenLanguage) throws ServiceFailedException;
}
