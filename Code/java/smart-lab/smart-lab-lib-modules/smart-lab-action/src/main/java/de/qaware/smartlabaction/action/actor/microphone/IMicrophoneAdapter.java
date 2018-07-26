package de.qaware.smartlabaction.action.actor.microphone;

import de.qaware.smartlabaction.action.actor.generic.IDeviceAdapter;

import java.nio.file.Path;

public interface IMicrophoneAdapter extends IDeviceAdapter {

    // TODO: Better names are "startRecording" and "stopRecording"
    void activate(Path recordingTargetFile);
    Path deactivate();
}
