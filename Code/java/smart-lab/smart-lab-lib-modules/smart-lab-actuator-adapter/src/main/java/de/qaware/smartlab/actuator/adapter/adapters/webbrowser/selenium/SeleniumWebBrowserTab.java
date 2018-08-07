package de.qaware.smartlab.actuator.adapter.adapters.webbrowser.selenium;

import de.qaware.smartlab.actuator.adapter.adapters.webbrowser.IWebBrowserTab;
import lombok.Getter;
import lombok.ToString;

import java.net.URL;

@Getter
@ToString
public class SeleniumWebBrowserTab implements IWebBrowserTab {

    private final URL initialUrl;
    private final String windowHandle;

    private SeleniumWebBrowserTab(URL initialUrl, String windowHandle) {
        this.initialUrl = initialUrl;
        this.windowHandle = windowHandle;
    }

    public static SeleniumWebBrowserTab of(URL initialUrl, String windowHandle) {
        return new SeleniumWebBrowserTab(initialUrl, windowHandle);
    }

    @Override
    public String getIdentifier() {
        return getWindowHandle();
    }
}
