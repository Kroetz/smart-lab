package de.qaware.smartlabapi.service.connector.gui;

import de.qaware.smartlabapi.service.client.gui.IGuiApiClient;
import de.qaware.smartlabcore.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
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
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IGuiApiClient guiApiClient;

        public BaseUrlGetter(IGuiApiClient guiApiClient) {
            this.guiApiClient = guiApiClient;
        }

        @Override
        public URL getBaseUrl() {
            // TODO: Exceptions
            try {
                return this.guiApiClient.getBaseUrl().getBody();
            }
            catch(RetryableException e) {
                throw e;
            }
            catch(FeignException e) {
                throw new UnknownErrorException();
            }
        }
    }
}
