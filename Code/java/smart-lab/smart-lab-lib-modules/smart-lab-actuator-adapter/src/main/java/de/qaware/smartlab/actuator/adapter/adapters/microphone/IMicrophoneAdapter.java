package de.qaware.smartlab.actuator.adapter.adapters.microphone;

import de.qaware.smartlab.core.data.actuator.IDeviceAdapter;

import java.nio.file.Path;

public interface IMicrophoneAdapter extends IDeviceAdapter {

    void startRecording(Path recordingTargetFile);
    Path stopRecording();
}
