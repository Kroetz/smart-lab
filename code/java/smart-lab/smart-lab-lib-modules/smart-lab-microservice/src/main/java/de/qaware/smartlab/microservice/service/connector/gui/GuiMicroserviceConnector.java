package de.qaware.smartlab.microservice.service.connector.gui;

import de.qaware.smartlab.api.service.client.gui.IGuiApiClient;
import de.qaware.smartlab.api.service.connector.gui.IGuiService;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.exception.SmartLabException;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MICROSERVICE)
public class GuiMicroserviceConnector implements IGuiService {

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_GUI_SERVICE_BASE_URL_GETTER)
    @ConditionalOnProperty(
            prefix = ModularityConfiguration.Properties.PREFIX,
            name = ModularityConfiguration.Properties.MODULARITY,
            havingValue = ModularityConfiguration.Properties.MICROSERVICE)
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
