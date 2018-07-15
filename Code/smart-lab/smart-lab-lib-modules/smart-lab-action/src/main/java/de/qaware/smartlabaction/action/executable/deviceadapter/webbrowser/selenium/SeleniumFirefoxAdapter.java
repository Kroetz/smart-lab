package de.qaware.smartlabaction.action.executable.deviceadapter.webbrowser.selenium;

import de.qaware.smartlabaction.action.executable.deviceadapter.webbrowser.IHotkeys;
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

    public SeleniumFirefoxAdapter(Path seleniumGeckoDriverFile) {
        super(DEVICE_TYPE, HAS_LOCAL_API, () -> new FirefoxDriver(), newTabHotkeys);
        // TODO: String literal
        System.setProperty("webdriver.gecko.driver", seleniumGeckoDriverFile.toString());
    }
}
