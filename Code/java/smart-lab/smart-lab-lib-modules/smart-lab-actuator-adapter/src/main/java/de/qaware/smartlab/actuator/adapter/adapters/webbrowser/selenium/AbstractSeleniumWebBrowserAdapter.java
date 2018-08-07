package de.qaware.smartlab.actuator.adapter.adapters.webbrowser.selenium;

import de.qaware.smartlab.actuator.adapter.adapters.webbrowser.AbstractWebBrowserAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.webbrowser.IHotkeys;
import de.qaware.smartlab.actuator.adapter.adapters.webbrowser.IWebBrowserTab;
import de.qaware.smartlab.actuator.adapter.adapters.windowhandling.windowhandler.IWindowHandler;
import de.qaware.smartlab.actuator.adapter.adapters.windowhandling.windowinfo.IWindowInfo;
import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.exception.LocalDeviceException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Slf4j
public abstract class AbstractSeleniumWebBrowserAdapter extends AbstractWebBrowserAdapter {

    protected final Supplier<WebDriver> webDriverSupplier;
    protected final IHotkeys newTabHotkeys;
    protected final Map<UUID, SeleniumWebBrowserInstance> webBrowserInstancesById;
    protected final IWindowHandler windowHandler;
    
    protected AbstractSeleniumWebBrowserAdapter(
            String webBrowserType,
            boolean hasLocalApi,
            Supplier<WebDriver> webDriverSupplier,
            IHotkeys newTabHotkeys,
            IWindowHandler windowHandler) {
        super(webBrowserType, hasLocalApi);
        this.webDriverSupplier = webDriverSupplier;
        this.newTabHotkeys = newTabHotkeys;
        this.windowHandler = windowHandler;
        this.webBrowserInstancesById = new HashMap<>();
    }

    private SeleniumWebBrowserInstance resolveWebBrowserInstance(UUID webBrowserInstanceId) {
        SeleniumWebBrowserInstance webBrowserInstance = this.webBrowserInstancesById.get(webBrowserInstanceId);
        // TODO: Exception message
        if(isNull(webBrowserInstance)) throw new LocalDeviceException("The specified web browser instance does not exist");
        return webBrowserInstance;
    }

    private IWebBrowserTab selectTab(WebDriver webDriver, IWebBrowserTab tab) {
        webDriver.switchTo().window(tab.getIdentifier());
        return tab;
    }

    @Override
    public UUID newWebBrowserInstance(URL url) {
        return newWebBrowserInstance(singletonList(url));
    }

    @Override
    public UUID newWebBrowserInstance(List<URL> urls) {
        UUID webBrowserInstanceId = UUID.randomUUID();
        WebDriver webDriver = this.webDriverSupplier.get();
        newTabs(webDriver, webBrowserInstanceId, urls);
        IWindowInfo webBrowserWindow = this.windowHandler.findWebBrowserWindow(webDriver);
        SeleniumWebBrowserInstance webBrowserInstance = SeleniumWebBrowserInstance.of(
                webBrowserInstanceId,
                webDriver,
                webBrowserWindow);
        this.webBrowserInstancesById.put(webBrowserInstance.getId(), webBrowserInstance);
        return webBrowserInstanceId;
    }

    @Override
    public IWebBrowserTab newTab(UUID webBrowserInstanceId, URL url) {
        return newTab(resolveWebBrowserInstance(webBrowserInstanceId).getWebDriver(), webBrowserInstanceId, url);
    }

    private IWebBrowserTab newTab(WebDriver webDriver, UUID webBrowserInstanceId, URL url) {
        log.info("Creating new tab for URL {}", url);
        // TODO: String literal
        webDriver.findElement(By.cssSelector("body")).sendKeys(this.newTabHotkeys.getCharSequence());
        webDriver.navigate().to(url);
        IWebBrowserTab newTab = SeleniumWebBrowserTab.of(url, webDriver.getWindowHandle());
        addToAutoOpenedTabs(webBrowserInstanceId, newTab);
        return newTab;
    }

    @Override
    public List<IWebBrowserTab> newTabs(UUID webBrowserInstanceId, List<URL> urls) {
        return newTabs(resolveWebBrowserInstance(webBrowserInstanceId).getWebDriver(), webBrowserInstanceId, urls);
    }

    private List<IWebBrowserTab> newTabs(WebDriver webDriver, UUID webBrowserInstanceId, List<URL> urls) {
        log.info("Creating new tabs for URLs {}", urls);
        return urls.stream()
                .map(url -> newTab(webDriver, webBrowserInstanceId, url))
                .collect(toList());
    }

    @Override
    public void closeIfUnchanged(UUID webBrowserInstanceId, IWebBrowserTab tab) {
        try {
            log.info("Closing tab {}", tab);
            WebDriver webDriver = resolveWebBrowserInstance(webBrowserInstanceId).getWebDriver();
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
    public void closeUnchangedAutoOpenedTabs(UUID webBrowserInstanceId) {
        log.info("Closing all automatically opened web browser tabs that were not manually changed");
        Set<IWebBrowserTab> autoOpenedTabs = this.autoOpenedTabsByWebBrowserInstanceId.get(webBrowserInstanceId);
        if(isNull(autoOpenedTabs)) {
            log.info("Cannot close auto opened tabs of web browser instance \"{}\" since it does not have any", webBrowserInstanceId);
            return;
        }
        for(IWebBrowserTab tab : autoOpenedTabs) {
            closeIfUnchanged(webBrowserInstanceId, tab);
        }
        this.webBrowserInstancesById.remove(webBrowserInstanceId);
        this.autoOpenedTabsByWebBrowserInstanceId.remove(webBrowserInstanceId);
    }

    @Override
    public void closeTab(UUID webBrowserInstanceId, IWebBrowserTab tab) {
        try {
            log.info("Closing tab {}", tab);
            WebDriver webDriver = resolveWebBrowserInstance(webBrowserInstanceId).getWebDriver();
            selectTab(webDriver, tab);
            webDriver.close();
        }
        catch(NoSuchWindowException e) {
            log.info("Cannot close tab {} since it does not exist", tab);
        }
    }

    @Override
    public void closeAllTabs(UUID webBrowserInstanceId) {
        log.info("Closing all web browser tabs");
        WebDriver webDriver = resolveWebBrowserInstance(webBrowserInstanceId).getWebDriver();
        webDriver.quit();
        this.webBrowserInstancesById.remove(webBrowserInstanceId);
        this.autoOpenedTabsByWebBrowserInstanceId.remove(webBrowserInstanceId);
    }

    @Override
    public void maximizeOnDisplay(UUID browserInstanceId, DeviceId displayId) {
        SeleniumWebBrowserInstance webBrowserInstance = resolveWebBrowserInstance(browserInstanceId);
        webBrowserInstance.getWindow().maximizeOnDisplay(displayId);
    }
}
