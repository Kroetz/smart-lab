package de.qaware.smartlab.actuator.adapter.adapters.microphone;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;

import java.nio.file.Path;

public interface IMicrophoneAdapter extends IActuatorAdapter {

    void startRecording(Path recordingTargetFile) throws ActuatorException;
    Path stopRecording() throws ActuatorException;
}
