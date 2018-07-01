package de.qaware.smartlabcore.data.device.webbrowser.selenium;

import de.qaware.smartlabcore.data.device.webbrowser.AbstractWebBrowser;
import de.qaware.smartlabcore.data.device.webbrowser.IHotkeys;
import de.qaware.smartlabcore.data.device.webbrowser.IWebBrowserTab;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractSeleniumWebBrowser extends AbstractWebBrowser {

    protected final WebDriver webDriver;
    protected final IHotkeys newTabHotkeys;

    protected AbstractSeleniumWebBrowser(
            WebDriver webDriver,
            IHotkeys newTabHotkeys) {
        this.webDriver = webDriver;
        this.newTabHotkeys = newTabHotkeys;
    }

    private IWebBrowserTab selectTab(IWebBrowserTab tab) {
        this.webDriver.switchTo().window(tab.getIdentifier());
        return tab;
    }

    @Override
    public IWebBrowserTab newTab(URL url) {
        log.info("Creating new tab for URL {}", url);
        // TODO: String literal
        this.webDriver.findElement(By.cssSelector("body")).sendKeys(this.newTabHotkeys.getCharSequence());
        this.webDriver.navigate().to(url);
        IWebBrowserTab newTab = SeleniumWebBrowserTab.of(url, this.webDriver.getWindowHandle());
        this.autoOpenedTabs.add(newTab);
        return newTab;
    }

    @Override
    public Set<IWebBrowserTab> newTabs(Set<URL> urls) {
        log.info("Creating new tabs for URLs {}", urls);
        return urls.stream()
                .map(this::newTab)
                .collect(Collectors.toSet());
    }

    @Override
    public void closeIfUnchanged(IWebBrowserTab tab) {
        try {
            log.info("Closing tab {}", tab);
            selectTab(tab);
            URL currentUrl = new URL(this.webDriver.getCurrentUrl());
            if(currentUrl.equals(tab.getInitialUrl())) {
                this.webDriver.close();
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
    public void closeUnchangedAutoOpenedTabs() {
        log.info("Closing all automatically opened web browser tabs that were not manually changed");
        for(IWebBrowserTab tab : autoOpenedTabs) {
            closeIfUnchanged(tab);
        }
    }

    @Override
    public void closeTab(IWebBrowserTab tab) {
        try {
            log.info("Closing tab {}", tab);
            selectTab(tab);
            this.webDriver.close();
        }
        catch(NoSuchWindowException e) {
            log.info("Cannot close tab {} since it does not exist", tab);
        }
    }

    @Override
    public void closeAllTabs() {
        log.info("Closing all web browser tabs");
        this.webDriver.quit();
    }
}
