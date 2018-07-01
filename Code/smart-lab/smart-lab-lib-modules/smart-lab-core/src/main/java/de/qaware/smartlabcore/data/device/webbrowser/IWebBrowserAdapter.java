package de.qaware.smartlabcore.data.device.webbrowser;

import de.qaware.smartlabcore.data.device.IDeviceAdapter;

import java.net.URL;
import java.util.List;

public interface IWebBrowserAdapter extends IDeviceAdapter {

    IWebBrowserTab newTab(URL url);
    List<IWebBrowserTab> newTabs(List<URL> urls);
    void closeIfUnchanged(IWebBrowserTab tab);
    void closeUnchangedAutoOpenedTabs();
    void closeTab(IWebBrowserTab tab);
    void closeAllTabs();
}
