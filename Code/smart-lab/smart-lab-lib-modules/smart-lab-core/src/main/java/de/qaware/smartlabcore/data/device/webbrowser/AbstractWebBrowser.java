package de.qaware.smartlabcore.data.device.webbrowser;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractWebBrowser implements IWebBrowser {

    protected final Set<IWebBrowserTab> autoOpenedTabs;

    protected AbstractWebBrowser() {
        this.autoOpenedTabs = new HashSet<>();
    }
}
