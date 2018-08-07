package de.qaware.smartlab.actuator.adapter.adapters.windowhandling.windowhandler;

import de.qaware.smartlab.actuator.adapter.adapters.windowhandling.windowinfo.IWindowInfo;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Slf4j
public abstract class AbstractWindowHandler<WindowT extends IWindowInfo> implements IWindowHandler {

    protected final Map<String, String> localDisplaysBySmartLabDisplayIds;
    protected final Duration findWindowTimeout;

    protected AbstractWindowHandler(
            Map<String, String> localDisplaysBySmartLabDisplayIds,
            Duration findWindowTimeout) {
        this.localDisplaysBySmartLabDisplayIds = localDisplaysBySmartLabDisplayIds;
        this.findWindowTimeout = findWindowTimeout;
    }

    protected String resolveLocalDisplay(DeviceId displayId) {
        String localDisplay = this.localDisplaysBySmartLabDisplayIds.get(displayId.getIdValue());
        if(isNull(localDisplay)) throw new IllegalArgumentException(format(
                "The display \"%s\" cannot be resolved to a local device", displayId.getIdValue()));
        return localDisplay;
    }

    protected WindowT castWindow(IWindowInfo window, Class<WindowT> windowClass) throws IllegalArgumentException {
        requireValidType(window, windowClass);
        return windowClass.cast(window);
    }

    protected void requireValidType(IWindowInfo window, Class<WindowT> windowClass) throws IllegalArgumentException {
        boolean hasValidType = window.getClass().equals(windowClass);
        if(!hasValidType) throw new IllegalArgumentException(format(
                "The passed window %s must be of the type %s", window, windowClass.getName()));
    }
}
