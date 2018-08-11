package de.qaware.smartlab.monolith.service.url;

import de.qaware.smartlab.core.concurrency.ThreadContext;
import de.qaware.smartlab.core.constant.Property;
import de.qaware.smartlab.core.service.url.IBaseUrlDetector;
import de.qaware.smartlab.monolith.configuration.MonolithModuleConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

import static de.qaware.smartlab.core.util.StringUtils.EMPTY;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
@Slf4j
public class MonolithicBaseUrlDetector implements IBaseUrlDetector {

    private final URL fallbackBaseUrl;

    public MonolithicBaseUrlDetector(
            @Qualifier(MonolithModuleConfiguration.QUALIFIER_FALLBACK_BASE_URL) URL fallbackBaseUrl) {
        this.fallbackBaseUrl = fallbackBaseUrl;
    }

    @Override
    public URL detect() throws MalformedURLException {
        URL associatedRequestUrl = ThreadContext.get()
                .getAssociatedRequestUrl()
                .orElse(this.fallbackBaseUrl);
        return new URL(
                associatedRequestUrl.getProtocol(),
                associatedRequestUrl.getHost(),
                associatedRequestUrl.getPort(),
                EMPTY);
    }
}
