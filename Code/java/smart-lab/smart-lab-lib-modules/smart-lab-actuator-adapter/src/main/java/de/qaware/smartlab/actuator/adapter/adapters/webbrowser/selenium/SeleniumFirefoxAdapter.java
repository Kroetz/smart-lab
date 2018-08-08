package de.qaware.smartlab.actuator.adapter.adapters.webbrowser.selenium;

import de.qaware.smartlab.actuator.adapter.adapters.webbrowser.IHotkeys;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler.IWindowHandler;
import de.qaware.smartlab.core.miscellaneous.Property;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.SELENIUM,
        name = Property.Name.SELENIUM,
        havingValue = Property.Value.TRUE)
@Slf4j
public class SeleniumFirefoxAdapter extends AbstractSeleniumWebBrowserAdapter {

    public static final String ACTUATOR_TYPE = "firefox";
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

    public SeleniumFirefoxAdapter(
            // TODO: String literals
            @Qualifier("seleniumGeckoDriverFile") Path seleniumGeckoDriverFile,
            IWindowHandler windowHandler) {
        super(ACTUATOR_TYPE, HAS_LOCAL_API, FirefoxDriver::new, newTabHotkeys, windowHandler);
        // TODO: String literal
        System.setProperty("webdriver.gecko.driver", seleniumGeckoDriverFile.toString());
    }
}
