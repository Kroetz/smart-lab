package de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler;

import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.exception.WindowHandlingException;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowinfo.IWindowInfo;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

public interface IWindowHandler {

    IWindowInfo findWebBrowserWindow(WebDriver webDriver) throws IllegalArgumentException, WindowHandlingException;
    IWindowInfo findPowerPointWindow(Path openedFile) throws WindowHandlingException;
    void maximizeOnDisplay(IWindowInfo window, DeviceId displayId) throws WindowHandlingException;
}
