package de.qaware.smartlab.core.data.action.speechtotext;

import de.qaware.smartlab.core.exception.ServiceFailedException;
import de.qaware.smartlab.core.miscellaneous.Language;

import java.nio.file.Path;

public interface ISpeechToTextService {

    String getServiceId();
    ITranscript speechToText(Path audioFile, Language spokenLanguage) throws ServiceFailedException;
}
