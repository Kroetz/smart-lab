package de.qaware.smartlabactuatoradapter.actuator.webbrowser;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class WebBrowserAdapterResolver extends AbstractResolver<String, IWebBrowserAdapter> {

    public WebBrowserAdapterResolver(Optional<List<IWebBrowserAdapter>> webBrowserAdapters) {
        super(getWebBrowserAdaptersByType(webBrowserAdapters));
    }

    private static Set<Map.Entry<String, IWebBrowserAdapter>> getWebBrowserAdaptersByType(Optional<List<IWebBrowserAdapter>> webBrowserAdapters) {
        return webBrowserAdapters
                .map(adapters -> adapters
                        .stream()
                        .collect(toMap(IWebBrowserAdapter::getDeviceType, identity()))
                        .entrySet())
                .orElse(new HashSet<>());
    }
}
