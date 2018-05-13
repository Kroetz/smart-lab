package de.qaware.smartlabcommons.data.device.microphone;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class AbstractMicrophoneAdapter implements IMicrophoneAdapter {

    // TODO: Every device adapter has a device type --> move to abstract base class
    protected String deviceType;

    public AbstractMicrophoneAdapter(String deviceType) {
        this.deviceType = deviceType;
    }
}
