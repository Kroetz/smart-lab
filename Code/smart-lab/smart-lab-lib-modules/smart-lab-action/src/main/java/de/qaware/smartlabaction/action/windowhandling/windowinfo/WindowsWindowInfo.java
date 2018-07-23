package de.qaware.smartlabaction.action.windowhandling.windowinfo;

import com.sun.jna.platform.win32.WinDef;
import de.qaware.smartlabcore.windowhandling.IWindowHandler;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Window info for Microsoft Windows operating systems.
 */
@Getter
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WindowsWindowInfo extends AbstractWindowInfo {

    private final WinDef.HWND windowHandle;

    private WindowsWindowInfo(
            IWindowHandler windowHandler,
            WinDef.HWND windowHandle) {
        super(windowHandler);
        this.windowHandle = windowHandle;
    }

    public static WindowsWindowInfo of(IWindowHandler windowHandler, WinDef.HWND windowHandle) {
        return new WindowsWindowInfo(windowHandler, windowHandle);
    }
}
