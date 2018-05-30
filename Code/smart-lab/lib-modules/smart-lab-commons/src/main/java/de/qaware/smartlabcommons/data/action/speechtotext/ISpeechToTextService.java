package de.qaware.smartlabcommons.data.action.speechtotext;

import de.qaware.smartlabcommons.exception.ServiceFailedException;

import java.nio.file.Path;

public interface ISpeechToTextService {

    ITranscript speechToText(Path audioFile) throws ServiceFailedException;
}
