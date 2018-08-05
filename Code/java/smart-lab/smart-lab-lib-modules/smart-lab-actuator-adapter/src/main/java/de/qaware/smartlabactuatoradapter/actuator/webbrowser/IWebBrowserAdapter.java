package de.qaware.smartlabactuatoradapter.actuator.webbrowser;

import de.qaware.smartlabactuatoradapter.actuator.generic.IDeviceAdapter;
import de.qaware.smartlabcore.data.device.entity.DeviceId;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public interface IWebBrowserAdapter extends IDeviceAdapter {

    UUID newWebBrowserInstance(URL url);
    UUID newWebBrowserInstance(List<URL> urls);
    IWebBrowserTab newTab(UUID browserInstanceId, URL url);
    List<IWebBrowserTab> newTabs(UUID browserInstanceId, List<URL> urls);
    void closeIfUnchanged(UUID browserInstanceId, IWebBrowserTab tab);
    void closeUnchangedAutoOpenedTabs(UUID browserInstanceId);
    void closeTab(UUID browserInstanceId, IWebBrowserTab tab);
    void closeAllTabs(UUID browserInstanceId);
    void maximizeOnDisplay(UUID browserInstanceId, DeviceId displayId);
}
