package de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler;

import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.exception.windowhandling.WindowHandlingException;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowinfo.IWindowInfo;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

public interface IWindowHandler {

    IWindowInfo findWebBrowserWindow(WebDriver webDriver) throws IllegalArgumentException, WindowHandlingException;
    IWindowInfo findPowerPointWindow(Path openedFile) throws IllegalArgumentException, WindowHandlingException;
    void maximizeOnDisplay(IWindowInfo window, ActuatorId displayId) throws WindowHandlingException;
}
