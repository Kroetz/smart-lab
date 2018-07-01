package de.qaware.smartlabcore.data.device.webbrowser;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WebBrowserAdapterResolver extends AbstractResolver<String, IWebBrowserAdapter> {

    public WebBrowserAdapterResolver(List<IWebBrowserAdapter> webBrowserAdapters) {
        super(WebBrowserAdapterResolver.getWebBrowserAdaptersByType(webBrowserAdapters));
    }

    private static Map<String, IWebBrowserAdapter> getWebBrowserAdaptersByType(List<IWebBrowserAdapter> webBrowserAdapters) {
        return webBrowserAdapters.stream().collect(Collectors.toMap(IWebBrowserAdapter::getDeviceType, Function.identity()));
    }
}
