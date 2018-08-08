package de.qaware.smartlab.actuator.adapter.adapters.generic;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class AbstractActuatorAdapter implements IActuatorAdapter {

    protected final String actuatorType;
    protected final boolean hasLocalApi;

    public AbstractActuatorAdapter(String actuatorType, boolean hasLocalApi) {
        this.actuatorType = actuatorType;
        this.hasLocalApi = hasLocalApi;
    }

    public boolean hasLocalApi() {
        return this.hasLocalApi;
    }
}
