package de.qaware.smartlabapi.service.connector.gui;

import de.qaware.smartlabapi.service.client.gui.IGuiApiClient;
import de.qaware.smartlabapi.service.url.AbstractMicroserviceBaseUrlGetter;
import de.qaware.smartlabcore.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class GuiMicroserviceConnector implements IGuiService {

    @Component
    // TODO: String literal
    @Qualifier("guiServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    public static class BaseUrlGetter extends AbstractMicroserviceBaseUrlGetter {

        public BaseUrlGetter(IGuiApiClient guiApiClient) {
            super(guiApiClient);
        }
    }
}
