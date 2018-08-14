package de.qaware.smartlab.actuator.adapter.adapters.beamer;

import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractActuatorAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractBeamerAdapter extends AbstractActuatorAdapter implements IBeamerAdapter {

    protected AbstractBeamerAdapter(String actuatorType, boolean hasLocalApi) {
        super(actuatorType, hasLocalApi);
    }
}
