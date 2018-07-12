package de.qaware.smartlabcore.service.url;

import de.qaware.smartlabcore.concurrency.ThreadContext;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.miscellaneous.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
@Slf4j
public class MonolithicBaseUrlDetector implements IBaseUrlDetector {

    private final URL fallbackBaseUrl;

    public MonolithicBaseUrlDetector(
            // TODO: String literal
            @Qualifier("fallbackBaseUrl") URL fallbackBaseUrl) {
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
                StringUtils.EMPTY);
    }
}
