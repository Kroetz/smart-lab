package de.qaware.smartlab.actuator.adapter.adapters.webbrowser;

import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractActuatorAdapter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Objects.isNull;

public abstract class AbstractWebBrowserAdapter extends AbstractActuatorAdapter implements IWebBrowserAdapter {

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
