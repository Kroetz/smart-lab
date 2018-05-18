package de.qaware.smartlabcommons.data.device.microphone;

import de.qaware.smartlabcommons.data.device.IDeviceAdapter;

public interface IMicrophoneAdapter extends IDeviceAdapter {

    // TODO: Better names are "startRecording" and "stopRecording"
    void activate();
    void deactivate();
}
