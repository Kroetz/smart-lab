package de.qaware.smartlabaction.action.executable.deviceadapter.webbrowser.selenium;

import de.qaware.smartlabaction.action.executable.deviceadapter.webbrowser.IHotkeys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@Slf4j
public class SeleniumChromeAdapter extends AbstractSeleniumWebBrowserAdapter {

    public static final String DEVICE_TYPE = "chrome";
    private static final boolean HAS_LOCAL_API = true;
    private static final IHotkeys newTabHotkeys = new IHotkeys() {
        @Override
        public CharSequence[] getCharSequence() {
            if(SystemUtils.IS_OS_MAC) {
                return new CharSequence[]{Keys.COMMAND, "t"};
            }
            return new CharSequence[]{Keys.CONTROL, "t"};
        }
    };

    public SeleniumChromeAdapter(Path seleniumChromeDriverFile) {
        super(DEVICE_TYPE, HAS_LOCAL_API, () -> new ChromeDriver(), newTabHotkeys);
        // TODO: String literal
        System.setProperty("webdriver.chrome.driver", seleniumChromeDriverFile.toString());
    }
}