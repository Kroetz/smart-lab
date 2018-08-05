package de.qaware.smartlabactuatoradapter.actuator.webbrowser.selenium;

import de.qaware.smartlabactuatoradapter.actuator.webbrowser.IHotkeys;
import de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowhandler.IWindowHandler;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;

@Component
@Slf4j
public class SeleniumChromeAdapter extends AbstractSeleniumWebBrowserAdapter {

    public static final String DEVICE_TYPE = "chrome";
    private static final boolean HAS_LOCAL_API = true;
    private static final IHotkeys newTabHotkeys = new IHotkeys() {
        @Override
        public CharSequence[] getCharSequence() {
            if(IS_OS_MAC) {
                return new CharSequence[]{Keys.COMMAND, "t"};
            }
            return new CharSequence[]{Keys.CONTROL, "t"};
        }
    };

    public SeleniumChromeAdapter(Path seleniumChromeDriverFile, IWindowHandler windowHandler) {
        super(DEVICE_TYPE, HAS_LOCAL_API, ChromeDriver::new, newTabHotkeys, windowHandler);
        // TODO: String literal
        System.setProperty("webdriver.chrome.driver", seleniumChromeDriverFile.toString());
    }
}
