package de.qaware.smartlab.actuator.adapter.adapters.windowhandling.windowhandler;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.WindowHandlingException;
import de.qaware.smartlab.actuator.adapter.adapters.windowhandling.windowinfo.IWindowInfo;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

public interface IWindowHandler {

    IWindowInfo findWebBrowserWindow(WebDriver webDriver) throws IllegalArgumentException, WindowHandlingException;
    IWindowInfo findPowerPointWindow(Path openedFile) throws WindowHandlingException;
    void maximizeOnDisplay(IWindowInfo window, DeviceId displayId) throws WindowHandlingException;
}
