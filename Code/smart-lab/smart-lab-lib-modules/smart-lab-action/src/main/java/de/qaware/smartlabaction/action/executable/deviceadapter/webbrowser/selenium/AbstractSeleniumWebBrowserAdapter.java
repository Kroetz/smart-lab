package de.qaware.smartlabaction.action.executable.deviceadapter.webbrowser.selenium;

import de.qaware.smartlabaction.action.executable.deviceadapter.webbrowser.AbstractWebBrowserAdapter;
import de.qaware.smartlabaction.action.executable.deviceadapter.webbrowser.IHotkeys;
import de.qaware.smartlabaction.action.executable.deviceadapter.webbrowser.IWebBrowserTab;
import de.qaware.smartlabcore.exception.LocalDeviceException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractSeleniumWebBrowserAdapter extends AbstractWebBrowserAdapter {

    protected final Supplier<WebDriver> webDriverSupplier;
    protected final IHotkeys newTabHotkeys;
    protected final Map<UUID, WebDriver> webDriversById;
    
    protected AbstractSeleniumWebBrowserAdapter(
            String webBrowserType,
            boolean hasLocalApi,
            Supplier<WebDriver> webDriverSupplier,
            IHotkeys newTabHotkeys) {
        super(webBrowserType, hasLocalApi);
        this.webDriverSupplier = webDriverSupplier;
        this.newTabHotkeys = newTabHotkeys;
        this.webDriversById = new HashMap<>();
    }

    private WebDriver resolveWebDriver(UUID webDriverId) {
        WebDriver webDriver = this.webDriversById.get(webDriverId);
        // TODO: Exception message
        if(Objects.isNull(webDriver)) throw new LocalDeviceException("The specified web driver instance does not exist");
        return webDriver;
    }

    private IWebBrowserTab selectTab(WebDriver webDriver, IWebBrowserTab tab) {
        webDriver.switchTo().window(tab.getIdentifier());
        return tab;
    }

    @Override
    public UUID newWebBrowserInstance() {
        UUID webDriverId = UUID.randomUUID();
        WebDriver webDriver = this.webDriverSupplier.get();
        this.webDriversById.put(webDriverId, webDriver);
        return webDriverId;
    }

    @Override
    public IWebBrowserTab newTab(UUID webDriverId, URL url) {
        log.info("Creating new tab for URL {}", url);
        WebDriver webDriver = resolveWebDriver(webDriverId);
        // TODO: String literal
        webDriver.findElement(By.cssSelector("body")).sendKeys(this.newTabHotkeys.getCharSequence());
        webDriver.navigate().to(url);
        IWebBrowserTab newTab = SeleniumWebBrowserTab.of(url, webDriver.getWindowHandle());
        this.autoOpenedTabs.add(newTab);
        return newTab;
    }

    @Override
    public List<IWebBrowserTab> newTabs(UUID webDriverId, List<URL> urls) {
        log.info("Creating new tabs for URLs {}", urls);
        return urls.stream()
                .map(url -> newTab(webDriverId, url))
                .collect(Collectors.toList());
    }

    @Override
    public void closeIfUnchanged(UUID webDriverId, IWebBrowserTab tab) {
        try {
            log.info("Closing tab {}", tab);
            WebDriver webDriver = resolveWebDriver(webDriverId);
            selectTab(webDriver, tab);
            URL currentUrl = new URL(webDriver.getCurrentUrl());
            if(currentUrl.equals(tab.getInitialUrl())) {
                webDriver.close();
            }
            else {
                log.info("Not closing tab {} since its initial URL was manually changed", tab);
            }
        }
        catch(MalformedURLException e) {
            log.info("Not closing tab {} since it has an invalid URL", tab);
        }
        catch(NoSuchWindowException e) {
            log.info("Cannot close tab {} since it does not exist", tab);
        }
    }

    @Override
    public void closeUnchangedAutoOpenedTabs(UUID webDriverId) {
        log.info("Closing all automatically opened web browser tabs that were not manually changed");
        for(IWebBrowserTab tab : autoOpenedTabs) {
            closeIfUnchanged(webDriverId, tab);
        }
        this.webDriversById.remove(webDriverId);
    }

    @Override
    public void closeTab(UUID webDriverId, IWebBrowserTab tab) {
        try {
            log.info("Closing tab {}", tab);
            WebDriver webDriver = resolveWebDriver(webDriverId);
            selectTab(webDriver, tab);
            webDriver.close();
        }
        catch(NoSuchWindowException e) {
            log.info("Cannot close tab {} since it does not exist", tab);
        }
    }

    @Override
    public void closeAllTabs(UUID webDriverId) {
        log.info("Closing all web browser tabs");
        WebDriver webDriver = resolveWebDriver(webDriverId);
        webDriver.quit();
        this.webDriversById.remove(webDriverId);
    }
}