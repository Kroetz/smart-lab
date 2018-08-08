package de.qaware.smartlab.core.data.action.speechtotext;

import de.qaware.smartlab.core.data.actuator.IDeviceAdapter;
import de.qaware.smartlab.core.exception.ServiceFailedException;
import de.qaware.smartlab.core.miscellaneous.Language;

import java.nio.file.Path;

public interface ISpeechToTextAdapter extends IDeviceAdapter {

    ITranscript speechToText(Path audioFile, Language spokenLanguage) throws ServiceFailedException;
}
