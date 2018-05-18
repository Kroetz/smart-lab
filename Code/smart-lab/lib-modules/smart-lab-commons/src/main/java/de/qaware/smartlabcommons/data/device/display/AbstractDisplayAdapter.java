package de.qaware.smartlabcommons.data.device.display;

import de.qaware.smartlabcommons.data.device.AbstractDeviceAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractDisplayAdapter extends AbstractDeviceAdapter implements IDisplayAdapter {

    public AbstractDisplayAdapter(String deviceType, boolean hasLocalApi) {
        super(deviceType, hasLocalApi);
    }
}
