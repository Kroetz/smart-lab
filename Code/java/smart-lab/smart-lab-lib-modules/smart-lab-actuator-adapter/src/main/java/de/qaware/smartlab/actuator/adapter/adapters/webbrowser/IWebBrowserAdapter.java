package de.qaware.smartlab.actuator.adapter.adapters.webbrowser;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public interface IWebBrowserAdapter extends IActuatorAdapter {

    UUID newWebBrowserInstance(URL url) throws ActuatorException;
    UUID newWebBrowserInstance(List<URL> urls) throws ActuatorException;
    IWebBrowserTab newTab(UUID browserInstanceId, URL url) throws ActuatorException;
    List<IWebBrowserTab> newTabs(UUID browserInstanceId, List<URL> urls) throws ActuatorException;
    void closeIfUnchanged(UUID browserInstanceId, IWebBrowserTab tab) throws ActuatorException;
    void closeUnchangedAutoOpenedTabs(UUID browserInstanceId) throws ActuatorException;
    void closeTab(UUID browserInstanceId, IWebBrowserTab tab) throws ActuatorException;
    void closeAllTabs(UUID browserInstanceId) throws ActuatorException;
    void maximizeOnDisplay(UUID browserInstanceId, ActuatorId displayId) throws ActuatorException;
}
