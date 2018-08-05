package de.qaware.smartlabactuatoradapter.actuator.webbrowser.selenium;

import de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowinfo.IWindowInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

@Getter
@Slf4j
@ToString
@EqualsAndHashCode
public class SeleniumWebBrowserInstance {

    private final UUID id;
    private final WebDriver webDriver;
    private final IWindowInfo window;

    private SeleniumWebBrowserInstance(UUID id, WebDriver webDriver, IWindowInfo window) {
        this.id = id;
        this.webDriver = webDriver;
        this.window = window;
    }

    public static SeleniumWebBrowserInstance of(UUID id, WebDriver webDriver, IWindowInfo window) {
        return new SeleniumWebBrowserInstance(id, webDriver, window);
    }
}
