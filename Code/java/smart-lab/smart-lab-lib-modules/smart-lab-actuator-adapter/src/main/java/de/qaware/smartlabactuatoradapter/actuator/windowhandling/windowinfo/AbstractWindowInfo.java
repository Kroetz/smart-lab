package de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowinfo;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowhandler.IWindowHandler;
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
    public void maximizeOnDisplay(DeviceId displayId) {
        this.windowHandler.maximizeOnDisplay(this, displayId);
    }
}
