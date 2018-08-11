package de.qaware.smartlab.microservice.service.connector.gui;

import de.qaware.smartlab.api.service.client.gui.IGuiApiClient;
import de.qaware.smartlab.api.service.connector.gui.IGuiService;
import de.qaware.smartlab.core.exception.SmartLabException;
import de.qaware.smartlab.core.constant.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
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
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_GUI_SERVICE_BASE_URL_GETTER)
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
            try {
                return this.guiApiClient.getBaseUrl().getBody();
            }
            // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
            catch (Exception e) {
                throw new SmartLabException(e);
            }
        }
    }
}
