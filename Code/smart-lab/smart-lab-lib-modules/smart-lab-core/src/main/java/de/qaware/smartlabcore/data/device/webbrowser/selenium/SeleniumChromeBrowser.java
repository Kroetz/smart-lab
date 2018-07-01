package de.qaware.smartlabcore.data.device.webbrowser.selenium;

import de.qaware.smartlabcore.data.device.webbrowser.IHotkeys;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumChromeBrowser extends AbstractSeleniumWebBrowser {

    private static IHotkeys newTabHotkeys = new IHotkeys() {
        @Override
        public CharSequence[] getCharSequence() {
            if(SystemUtils.IS_OS_MAC) {
                return new CharSequence[]{Keys.COMMAND, "t"};
            }
            return new CharSequence[]{Keys.CONTROL, "t"};
        }
    };

    private SeleniumChromeBrowser() {
        super(new ChromeDriver(), newTabHotkeys);
    }

    public static SeleniumChromeBrowser newInstance() {
        return new SeleniumChromeBrowser();
    }
}
