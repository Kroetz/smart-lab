package de.qaware.smartlabactuatoradapter.actuator.microphone;

import de.qaware.smartlabactuatoradapter.actuator.generic.IDeviceAdapter;

import java.nio.file.Path;

public interface IMicrophoneAdapter extends IDeviceAdapter {

    // TODO: Better names are "startRecording" and "stopRecording"
    void activate(Path recordingTargetFile);
    Path deactivate();
}
