package de.qaware.smartlab.actuator.adapter.adapters.webbrowser;

import de.qaware.smartlab.core.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.emptySet;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class WebBrowserAdapterResolver extends AbstractResolver<String, IWebBrowserAdapter> {

    public WebBrowserAdapterResolver(Optional<List<IWebBrowserAdapter>> webBrowserAdapters) {
        super(getWebBrowserAdaptersByActuatorType(webBrowserAdapters));
    }

    private static Set<Map.Entry<String, IWebBrowserAdapter>> getWebBrowserAdaptersByActuatorType(Optional<List<IWebBrowserAdapter>> webBrowserAdapters) {
        return webBrowserAdapters
                .map(adapters -> adapters
                        .stream()
                        .collect(toMap(IWebBrowserAdapter::getActuatorType, identity()))
                        .entrySet())
                .orElse(emptySet());
    }

    @Override
    protected String getErrorMessage(String actuatorType) {
        return format("The web browser type \"%s\" is unknown", actuatorType);
    }
}
