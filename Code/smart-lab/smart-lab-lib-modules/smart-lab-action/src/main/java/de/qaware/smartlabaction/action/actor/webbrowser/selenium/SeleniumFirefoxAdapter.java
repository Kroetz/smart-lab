package de.qaware.smartlabaction.action.actor.webbrowser.selenium;

import de.qaware.smartlabaction.action.actor.webbrowser.IHotkeys;
import de.qaware.smartlabcore.windowhandling.IWindowHandler;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;

@Component
@Slf4j
public class SeleniumFirefoxAdapter extends AbstractSeleniumWebBrowserAdapter {

    public static final String DEVICE_TYPE = "firefox";
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

    public SeleniumFirefoxAdapter(Path seleniumGeckoDriverFile, IWindowHandler windowHandler) {
        super(DEVICE_TYPE, HAS_LOCAL_API, FirefoxDriver::new, newTabHotkeys, windowHandler);
        // TODO: String literal
        System.setProperty("webdriver.gecko.driver", seleniumGeckoDriverFile.toString());
    }
}
