package de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractProjectBaseInfoFactory implements IProjectBaseInfoFactory {

    protected final String actuatorType;

    protected AbstractProjectBaseInfoFactory(String actuatorType) {
        this.actuatorType = actuatorType;
    }

    @Override
    public String getActuatorType() {
        return this.actuatorType;
    }
}
