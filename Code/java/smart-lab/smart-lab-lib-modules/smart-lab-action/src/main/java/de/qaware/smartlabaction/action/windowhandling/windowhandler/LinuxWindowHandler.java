package de.qaware.smartlabaction.action.windowhandling.windowhandler;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.WindowHandlingException;
import de.qaware.smartlabcore.windowhandling.IWindowInfo;
import de.qaware.smartlabaction.action.windowhandling.windowinfo.LinuxWindowInfo;
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
    public void maximizeOnDisplay(IWindowInfo window, DeviceId displayId) throws WindowHandlingException {
        // TODO: Implement maximizing windows on Linux operating systems
        log.warn("Maximizing windows is not yet implemented for Linux");
    }
}
