package de.qaware.smartlabapi.service.gui;

import de.qaware.smartlabapi.client.IGuiApiClient;
import de.qaware.smartlabapi.service.IServiceBaseUrlGetter;
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
public class GuiMicroservice implements IGuiService {

    @Component
    // TODO: String literal
    @Qualifier("guiServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IGuiApiClient guiApiClient;

        public BaseUrlGetter(IGuiApiClient guiApiClient) {
            this.guiApiClient = guiApiClient;
        }

        @Override
        public URL getBaseUrl() {
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
