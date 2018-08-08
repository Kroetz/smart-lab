package de.qaware.smartlab.actuator.adapter.adapters.display;

import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractActuatorAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractDisplayAdapter extends AbstractActuatorAdapter implements IDisplayAdapter {

    public AbstractDisplayAdapter(String actuatorType, boolean hasLocalApi) {
        super(actuatorType, hasLocalApi);
    }
}
