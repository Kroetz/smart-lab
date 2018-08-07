package de.qaware.smartlab.actuator.adapter.adapters.beamer;

import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractDeviceAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractBeamerAdapter extends AbstractDeviceAdapter implements IBeamerAdapter {

    public AbstractBeamerAdapter(String deviceType, boolean hasLocalApi) {
        super(deviceType, hasLocalApi);
    }
}
