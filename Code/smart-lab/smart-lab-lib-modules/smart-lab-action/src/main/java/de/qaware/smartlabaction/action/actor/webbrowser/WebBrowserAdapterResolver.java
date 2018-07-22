package de.qaware.smartlabaction.action.actor.webbrowser;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class WebBrowserAdapterResolver extends AbstractResolver<String, IWebBrowserAdapter> {

    public WebBrowserAdapterResolver(List<IWebBrowserAdapter> webBrowserAdapters) {
        super(getWebBrowserAdaptersByType(webBrowserAdapters));
    }

    private static Set<Map.Entry<String, IWebBrowserAdapter>> getWebBrowserAdaptersByType(List<IWebBrowserAdapter> webBrowserAdapters) {
        return webBrowserAdapters.stream()
                .collect(toMap(IWebBrowserAdapter::getDeviceType, identity()))
                .entrySet();
    }
}
