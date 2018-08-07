package de.qaware.smartlab.actuator.adapter.adapters.display;

import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractDeviceAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractDisplayAdapter extends AbstractDeviceAdapter implements IDisplayAdapter {

    public AbstractDisplayAdapter(String deviceType, boolean hasLocalApi) {
        super(deviceType, hasLocalApi);
    }
}
