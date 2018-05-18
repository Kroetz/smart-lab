package de.qaware.smartlabcommons.data.device.microphone;

import de.qaware.smartlabcommons.data.device.AbstractDeviceAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractMicrophoneAdapter extends AbstractDeviceAdapter implements IMicrophoneAdapter {

    public AbstractMicrophoneAdapter(String deviceType, boolean hasLocalApi) {
        super(deviceType, hasLocalApi);
    }
}
