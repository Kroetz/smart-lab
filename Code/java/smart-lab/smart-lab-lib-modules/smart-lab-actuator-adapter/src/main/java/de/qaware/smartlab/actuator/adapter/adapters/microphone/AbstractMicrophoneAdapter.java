package de.qaware.smartlab.actuator.adapter.adapters.microphone;

import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractActuatorAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractMicrophoneAdapter extends AbstractActuatorAdapter implements IMicrophoneAdapter {

    public AbstractMicrophoneAdapter(String actuatorType, boolean hasLocalApi) {
        super(actuatorType, hasLocalApi);
    }
}
