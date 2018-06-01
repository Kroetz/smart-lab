package de.qaware.smartlabcore.data.device.microphone;

import de.qaware.smartlabcore.data.device.IDeviceAdapter;

import java.nio.file.Path;

public interface IMicrophoneAdapter extends IDeviceAdapter {

    // TODO: Better names are "startRecording" and "stopRecording"
    void activate(Path recordingTargetFile);
    Path deactivate();
}
