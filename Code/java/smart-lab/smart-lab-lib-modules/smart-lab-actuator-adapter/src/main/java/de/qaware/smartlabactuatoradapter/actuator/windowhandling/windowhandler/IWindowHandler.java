package de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowhandler;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.WindowHandlingException;
import de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowinfo.IWindowInfo;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

public interface IWindowHandler {

    IWindowInfo findWebBrowserWindow(WebDriver webDriver) throws IllegalArgumentException, WindowHandlingException;
    IWindowInfo findPowerPointWindow(Path openedFile) throws WindowHandlingException;
    void maximizeOnDisplay(IWindowInfo window, DeviceId displayId) throws WindowHandlingException;
}
