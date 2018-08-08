package de.qaware.smartlab.actuator.adapter.windowhandling.windowinfo;

import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler.IWindowHandler;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@EqualsAndHashCode
public abstract class AbstractWindowInfo implements IWindowInfo {

    protected final IWindowHandler windowHandler;

    protected AbstractWindowInfo(IWindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }

    @Override
    public void maximizeOnDisplay(ActuatorId displayId) {
        this.windowHandler.maximizeOnDisplay(this, displayId);
    }
}
