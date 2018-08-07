package de.qaware.smartlab.actuator.adapter.adapters.windowhandling.windowinfo;

import de.qaware.smartlab.actuator.adapter.adapters.windowhandling.windowhandler.IWindowHandler;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Window info for Linux operating systems.
 */
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LinuxWindowInfo extends AbstractWindowInfo {

    private LinuxWindowInfo(IWindowHandler windowHandler) {
        super(windowHandler);
    }

    public static LinuxWindowInfo of(IWindowHandler windowHandler) {
        return new LinuxWindowInfo(windowHandler);
    }
}
