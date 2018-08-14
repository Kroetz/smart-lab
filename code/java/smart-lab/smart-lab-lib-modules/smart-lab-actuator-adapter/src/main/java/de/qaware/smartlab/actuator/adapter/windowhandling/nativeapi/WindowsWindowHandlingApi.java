package de.qaware.smartlab.actuator.adapter.windowhandling.nativeapi;

import com.sun.jna.Library;
import com.sun.jna.platform.win32.WinDef;

/**
 * Window handling API for Microsoft Windows operating systems.
 */
public interface WindowsWindowHandlingApi extends Library {

    WinDef.HWND FindWindowByTitle(String windowTitle);
    WinDef.HWND FindForegroundWindow();
    void MoveToFullScreen(WinDef.HWND windowHandle, String displayName);
}
