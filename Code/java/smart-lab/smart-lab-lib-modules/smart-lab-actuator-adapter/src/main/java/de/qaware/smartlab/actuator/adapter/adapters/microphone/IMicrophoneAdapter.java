package de.qaware.smartlab.actuator.adapter.adapters.microphone;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;

import java.nio.file.Path;

public interface IMicrophoneAdapter extends IActuatorAdapter {

    void startRecording(Path recordingTargetFile);
    Path stopRecording();
}
