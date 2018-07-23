package de.qaware.smartlabcore.windowhandling;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.WindowHandlingException;
import org.openqa.selenium.WebDriver;

public interface IWindowHandler {

    IWindowInfo findWebBrowserWindow(WebDriver webDriver) throws IllegalArgumentException, WindowHandlingException;
    void maximizeOnDisplay(IWindowInfo window, DeviceId displayId) throws WindowHandlingException;
}
