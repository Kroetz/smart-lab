package de.qaware.smartlabcore.data.device.webbrowser;

import de.qaware.smartlabcore.data.device.AbstractDeviceAdapter;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractWebBrowserAdapter extends AbstractDeviceAdapter implements IWebBrowserAdapter {

    protected final Set<IWebBrowserTab> autoOpenedTabs;

    protected AbstractWebBrowserAdapter(String webBrowserType, boolean hasLocalApi) {
        super(webBrowserType, hasLocalApi);
        this.autoOpenedTabs = new HashSet<>();
    }
}
