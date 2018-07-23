package de.qaware.smartlabaction.action.windowhandling.windowinfo;

import de.qaware.smartlabcore.windowhandling.IWindowHandler;
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
