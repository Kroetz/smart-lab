package de.qaware.smartlabcommons.data.device.microphone;

import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.data.room.IRoom;

public interface IMicrophoneAdapter {

    String getDeviceType();
    // TODO: Better names are "startRecording" and "stopRecording"
    void activate(IRoom room, IDevice device, boolean executeLocally);
    void deactivate(IRoom room, IDevice device, boolean executeLocally);
}
