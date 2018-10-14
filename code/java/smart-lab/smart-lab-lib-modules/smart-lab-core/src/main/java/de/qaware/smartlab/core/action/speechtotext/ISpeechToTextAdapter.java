package de.qaware.smartlab.core.action.speechtotext;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;
import de.qaware.smartlab.core.miscellaneous.Language;

import java.nio.file.Path;

public interface ISpeechToTextAdapter extends IActuatorAdapter {

    ITranscript speechToText(Path audioFile, Language spokenLanguage) throws ActuatorException;
}
