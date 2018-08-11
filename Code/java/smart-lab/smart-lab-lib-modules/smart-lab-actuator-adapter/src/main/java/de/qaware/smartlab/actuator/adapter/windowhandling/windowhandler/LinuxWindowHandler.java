package de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler;

import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.exception.windowhandling.WindowHandlingException;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowinfo.IWindowInfo;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowinfo.LinuxWindowInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

/**
 * Window handler for Linux operating systems.
 */
@Slf4j
public class LinuxWindowHandler extends AbstractWindowHandler<LinuxWindowInfo> {

    public LinuxWindowHandler(
            Map<String, String> localDisplaysBySmartLabDisplayIds,
            Duration findWindowTimeout) {
        super(localDisplaysBySmartLabDisplayIds, findWindowTimeout);
    }

    @Override
    public IWindowInfo findWebBrowserWindow(WebDriver webDriver) throws IllegalArgumentException, WindowHandlingException {
        // TODO: Implement finding windows on Linux operating systems
        log.warn("Finding windows is not yet implemented for Linux");
        return LinuxWindowInfo.of(this);
    }

    @Override
    public IWindowInfo findPowerPointWindow(Path openedFile) throws WindowHandlingException {
        // TODO: Implement finding windows on Linux operating systems
        log.warn("Finding windows is not yet implemented for Linux");
        return LinuxWindowInfo.of(this);
    }

    @Override
    public void maximizeOnDisplay(IWindowInfo window, ActuatorId displayId) throws WindowHandlingException {
        // TODO: Implement maximizing windows on Linux operating systems
        log.warn("Maximizing windows is not yet implemented for Linux");
    }
}
