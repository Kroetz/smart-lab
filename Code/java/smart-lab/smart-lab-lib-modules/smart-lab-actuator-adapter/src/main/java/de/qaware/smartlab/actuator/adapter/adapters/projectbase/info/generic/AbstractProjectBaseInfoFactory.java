package de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractProjectBaseInfoFactory implements IProjectBaseInfoFactory {

    protected final String deviceType;

    public AbstractProjectBaseInfoFactory(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String getDeviceType() {
        return this.deviceType;
    }
}
