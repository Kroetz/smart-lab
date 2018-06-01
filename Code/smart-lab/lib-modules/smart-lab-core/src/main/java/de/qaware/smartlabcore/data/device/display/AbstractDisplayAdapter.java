package de.qaware.smartlabcore.data.device.display;

import de.qaware.smartlabcore.data.device.AbstractDeviceAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractDisplayAdapter extends AbstractDeviceAdapter implements IDisplayAdapter {

    public AbstractDisplayAdapter(String deviceType, boolean hasLocalApi) {
        super(deviceType, hasLocalApi);
    }
}
