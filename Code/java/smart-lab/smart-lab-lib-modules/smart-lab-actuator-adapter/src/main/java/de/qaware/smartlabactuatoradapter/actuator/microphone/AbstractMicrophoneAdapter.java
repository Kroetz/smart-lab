package de.qaware.smartlabactuatoradapter.actuator.microphone;

import de.qaware.smartlabactuatoradapter.actuator.generic.AbstractDeviceAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractMicrophoneAdapter extends AbstractDeviceAdapter implements IMicrophoneAdapter {

    public AbstractMicrophoneAdapter(String deviceType, boolean hasLocalApi) {
        super(deviceType, hasLocalApi);
    }
}
