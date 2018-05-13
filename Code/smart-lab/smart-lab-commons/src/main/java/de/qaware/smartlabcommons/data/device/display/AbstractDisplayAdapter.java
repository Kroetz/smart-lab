package de.qaware.smartlabcommons.data.device.display;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class AbstractDisplayAdapter implements IDisplayAdapter {

    // TODO: Every device adapter has a device type --> move to abstract base class
    protected String deviceType;

    public AbstractDisplayAdapter(String deviceType) {
        this.deviceType = deviceType;
    }
}
