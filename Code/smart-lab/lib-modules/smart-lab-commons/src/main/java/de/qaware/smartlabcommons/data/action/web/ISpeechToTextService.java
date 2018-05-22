package de.qaware.smartlabcommons.data.action.web;

import de.qaware.smartlabcommons.exception.ServiceFailedException;

import java.nio.file.Path;

public interface ISpeechToTextService {

    ITranscript speechToText(Path audioFile) throws ServiceFailedException;
}
