package de.qaware.smartlabcore.data.device.webbrowser.selenium;

import de.qaware.smartlabcore.data.device.webbrowser.IHotkeys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SeleniumFirefoxAdapter extends AbstractSeleniumWebBrowserAdapter {

    public static final String DEVICE_TYPE = "firefox";
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

    private SeleniumFirefoxAdapter() {
        super(DEVICE_TYPE, HAS_LOCAL_API, new FirefoxDriver(), newTabHotkeys);
    }

    public static SeleniumFirefoxAdapter newInstance() {
        return new SeleniumFirefoxAdapter();
    }
}
