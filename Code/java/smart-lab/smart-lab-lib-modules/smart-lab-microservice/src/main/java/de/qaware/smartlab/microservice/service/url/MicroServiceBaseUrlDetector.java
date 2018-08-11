package de.qaware.smartlab.microservice.service.url;

import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.service.url.IBaseUrlDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

import static de.qaware.smartlab.core.util.StringUtils.EMPTY;

@Component
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MICROSERVICE)
@Slf4j
public class MicroServiceBaseUrlDetector implements IBaseUrlDetector {

    @Override
    public URL detect() throws MalformedURLException {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        URL requestUrl = builder.build().toUri().toURL();
        return new URL(
                requestUrl.getProtocol(),
                requestUrl.getHost(),
                requestUrl.getPort(),
                EMPTY);
    }
}
