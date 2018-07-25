package de.qaware.smartlabaction.action.windowhandling.windowhandler;

import com.sun.jna.platform.win32.WinDef;
import de.qaware.smartlabaction.action.windowhandling.nativeapi.WindowsWindowHandlingApi;
import de.qaware.smartlabaction.action.windowhandling.windowinfo.WindowsWindowInfo;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.WindowHandlingException;
import de.qaware.smartlabcore.windowhandling.IWindowInfo;
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
import static de.qaware.smartlabcore.miscellaneous.StringUtils.utf8ToBase64String;
import static java.lang.String.format;
import static java.util.Objects.isNull;

/**
 * Window handler for Microsoft Windows operating systems.
 */
@Slf4j
public class WindowsWindowHandler extends AbstractWindowHandler<WindowsWindowInfo> {

    /*
     * The time that must pass after a failed attempt before the Windows API is polled again to find a specific window.
     * NOTE: This interval should be specified rather too big than too small because windows that are about to appear
     * may reported as present by Windows. However trying to move them will result in their application to crash.
     */
    private static final Duration FIND_WINDOW_RETRY_INTERVAL = Duration.ofMillis(1000);

    private static final String FIREFOX_WINDOW_TITLE_TEMPLATE = "%s - Mozilla Firefox";
    private static final String CHROME_WINDOW_TITLE_TEMPLATE = "%s - Google Chrome";
    // TODO: This template is only valid for german versions of PowerPoint and will fail for other languages
    private static final String POWER_POINT_WINDOW_TITLE_TEMPLATE = "PowerPoint-Bildschirmpräsentation  -  %s";

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

    @Override
    public IWindowInfo findPowerPointWindow(Path openedFile) throws WindowHandlingException {
        String windowTitle = format(POWER_POINT_WINDOW_TITLE_TEMPLATE, openedFile.getFileName());
        return findWindowByTitle(windowTitle);
    }

    private IWindowInfo findWindowByTitle(String windowTitle) throws WindowHandlingException {
        /*
         * TODO: Converting the window title into a Base64 string is a workaround
         * UTF-8 String parameters get at some point converted wrongly when making calls to the window handling dll
         * so that they may contain invalid special chars.
         * The error must either happen in JNA or in the .dll file that is created by the Visual Studio Nuget package
         * "DllExport". If a misconfiguration is the root cause or JNA and DllExport are not compatible could not be
         * determined as of now. The chosen workaround for this problem is to convert UTF-8 strings to Base64 strings
         * and pass them to the library. There they are converted back into UTF-8 strings.
         */
        String windowTitleAsBase64 = utf8ToBase64String(windowTitle);
        WinDef.HWND windowHandle = null;
        Instant start = Instant.now();
        while(isNull(windowHandle) && Instant.now().isBefore(start.plus(this.findWindowTimeout))) {
            windowHandle = this.windowHandlingApi.FindWindowByTitle(windowTitleAsBase64);
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
