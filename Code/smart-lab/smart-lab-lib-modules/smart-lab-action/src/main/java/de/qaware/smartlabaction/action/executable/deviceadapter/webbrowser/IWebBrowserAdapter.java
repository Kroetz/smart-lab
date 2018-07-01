package de.qaware.smartlabaction.action.executable.deviceadapter.webbrowser;

import de.qaware.smartlabcore.data.device.IDeviceAdapter;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public interface IWebBrowserAdapter extends IDeviceAdapter {

    public UUID newWebBrowserInstance();
    IWebBrowserTab newTab(UUID browserInstanceId, URL url);
    List<IWebBrowserTab> newTabs(UUID browserInstanceId, List<URL> urls);
    void closeIfUnchanged(UUID browserInstanceId, IWebBrowserTab tab);
    void closeUnchangedAutoOpenedTabs(UUID browserInstanceId);
    void closeTab(UUID browserInstanceId, IWebBrowserTab tab);
    void closeAllTabs(UUID browserInstanceId);
}
