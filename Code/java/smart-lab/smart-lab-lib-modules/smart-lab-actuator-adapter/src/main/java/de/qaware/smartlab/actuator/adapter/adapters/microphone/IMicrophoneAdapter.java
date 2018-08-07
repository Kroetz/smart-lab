package de.qaware.smartlab.actuator.adapter.adapters.microphone;

import de.qaware.smartlab.actuator.adapter.adapters.generic.IDeviceAdapter;

import java.nio.file.Path;

public interface IMicrophoneAdapter extends IDeviceAdapter {

    // TODO: Better names are "startRecording" and "stopRecording"
    void activate(Path recordingTargetFile);
    Path deactivate();
}
