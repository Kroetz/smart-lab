package de.qaware.smartlabcore.data.action.speechtotext;

import de.qaware.smartlabcore.exception.ServiceFailedException;

import java.nio.file.Path;

public interface ISpeechToTextService {

    ITranscript speechToText(Path audioFile) throws ServiceFailedException;
}
