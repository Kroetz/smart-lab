package de.qaware.smartlabcore.data.device.webbrowser;

import java.net.URL;
import java.util.Set;

public interface IWebBrowser {

    IWebBrowserTab newTab(URL url);
    Set<IWebBrowserTab> newTabs(Set<URL> urls);
    void closeIfUnchanged(IWebBrowserTab tab);
    void closeUnchangedAutoOpenedTabs();
    void closeTab(IWebBrowserTab tab);
    void closeAllTabs();
}
