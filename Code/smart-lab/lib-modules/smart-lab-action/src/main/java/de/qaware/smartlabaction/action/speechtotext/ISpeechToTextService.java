package de.qaware.smartlabaction.action.speechtotext;

import de.qaware.smartlabcommons.exception.ServiceFailedException;

import java.nio.file.Path;

public interface ISpeechToTextService {

    ITranscript speechToText(Path audioFile) throws ServiceFailedException;
}
