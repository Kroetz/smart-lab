package de.qaware.smartlabcore.url;

import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.miscellaneous.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
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
                StringUtils.EMPTY);
    }
}
