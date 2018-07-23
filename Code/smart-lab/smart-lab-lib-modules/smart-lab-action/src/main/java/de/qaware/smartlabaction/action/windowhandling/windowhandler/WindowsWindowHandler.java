package de.qaware.smartlabaction.action.windowhandling.windowhandler;

import com.sun.jna.platform.win32.WinDef;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.WindowHandlingException;
import de.qaware.smartlabcore.windowhandling.IWindowInfo;
import de.qaware.smartlabaction.action.windowhandling.nativeapi.WindowsWindowHandlingApi;
import de.qaware.smartlabaction.action.windowhandling.windowinfo.WindowsWindowInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.sun.jna.Native.loadLibrary;
import static java.lang.String.format;
import static java.util.Objects.isNull;

/**
 * Window handler for Microsoft Windows operating systems.
 */
@Slf4j
public class WindowsWindowHandler extends AbstractWindowHandler<WindowsWindowInfo> {

    private static final Duration FIND_WINDOW_RETRY_INTERVAL = Duration.ofMillis(100);
    private static final String FIREFOX_WINDOW_TITLE_TEMPLATE = "%s - Mozilla Firefox";
    private static final String CHROME_WINDOW_TITLE_TEMPLATE = "%s - Google Chrome";

    private final WindowsWindowHandlingApi windowHandlingApi;

    public WindowsWindowHandler(
            Path windowHandlingDll,
            Map<String, String> localDisplaysBySmartLabDisplayIds,
            Duration findWindowTimeout) {
        super(localDisplaysBySmartLabDisplayIds, findWindowTimeout);
        this.windowHandlingApi = loadLibrary(windowHandlingDll.toString(), WindowsWindowHandlingApi.class);
    }

    @Override
    public IWindowInfo findWebBrowserWindow(WebDriver webDriver) throws IllegalArgumentException, WindowHandlingException {
        String windowTitle;
        if(webDriver instanceof FirefoxDriver) windowTitle = format(FIREFOX_WINDOW_TITLE_TEMPLATE, webDriver.getTitle());
        else if(webDriver instanceof ChromeDriver) windowTitle = format(CHROME_WINDOW_TITLE_TEMPLATE, webDriver.getTitle());
        else throw new IllegalArgumentException(webDriver.toString());
        return findWindowByTitle(windowTitle);
    }

    private IWindowInfo findWindowByTitle(String windowTitle) throws WindowHandlingException {
        WinDef.HWND windowHandle = null;
        Instant start = Instant.now();
        while(isNull(windowHandle) && Instant.now().isBefore(start.plus(this.findWindowTimeout))) {
            windowHandle = this.windowHandlingApi.FindWindowByTitle(windowTitle);
            try {
                TimeUnit.MILLISECONDS.sleep(FIND_WINDOW_RETRY_INTERVAL.toMillis());
            } catch (InterruptedException e) {
                String errorMessage = format("Could not wait and retry to find a window with the title \"%s\"", windowTitle);
                log.error(errorMessage, e);
                throw new WindowHandlingException(errorMessage, e);
            }
        }
        if(isNull(windowHandle)) {
            String errorMessage = format("Could not find a window with the title \"%s\"", windowTitle);
            log.error(errorMessage);
            throw new WindowHandlingException(errorMessage);
        }
        return WindowsWindowInfo.of(this, windowHandle);
    }

    @Override
    public void maximizeOnDisplay(IWindowInfo window, DeviceId displayId) {
        WindowsWindowInfo windowsWindow = castWindow(window, WindowsWindowInfo.class);
        this.windowHandlingApi.MoveToFullScreen(windowsWindow.getWindowHandle(), resolveLocalDisplay(displayId));
    }
}
