package de.qaware.smartlabactuatoradapter.actuator.webbrowser;

import de.qaware.smartlabactuatoradapter.actuator.generic.AbstractDeviceAdapter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Objects.isNull;

public abstract class AbstractWebBrowserAdapter extends AbstractDeviceAdapter implements IWebBrowserAdapter {

    protected final ConcurrentMap<UUID, Set<IWebBrowserTab>> autoOpenedTabsByWebBrowserInstanceId;

    protected AbstractWebBrowserAdapter(String webBrowserType, boolean hasLocalApi) {
        super(webBrowserType, hasLocalApi);
        this.autoOpenedTabsByWebBrowserInstanceId = new ConcurrentHashMap<>();
    }

    protected void addToAutoOpenedTabs(UUID webBrowserInstanceId, IWebBrowserTab tab) {
        Set<IWebBrowserTab> autoOpenedTabs = this.autoOpenedTabsByWebBrowserInstanceId.get(webBrowserInstanceId);
        if(isNull(autoOpenedTabs)) autoOpenedTabs = new HashSet<>();
        autoOpenedTabs.add(tab);
        this.autoOpenedTabsByWebBrowserInstanceId.put(webBrowserInstanceId, autoOpenedTabs);
    }
}
