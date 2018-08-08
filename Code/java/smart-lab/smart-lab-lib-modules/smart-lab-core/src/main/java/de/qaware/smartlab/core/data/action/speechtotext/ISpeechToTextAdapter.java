package de.qaware.smartlab.core.data.action.speechtotext;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.core.exception.ServiceFailedException;
import de.qaware.smartlab.core.miscellaneous.Language;

import java.nio.file.Path;

public interface ISpeechToTextAdapter extends IActuatorAdapter {

    ITranscript speechToText(Path audioFile, Language spokenLanguage) throws ServiceFailedException;
}
