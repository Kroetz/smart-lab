package de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractProjectBaseInfoFactory implements IProjectBaseInfoFactory {

    protected final String serviceId;

    public AbstractProjectBaseInfoFactory(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceId() {
        return this.serviceId;
    }
}
