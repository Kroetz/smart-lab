package de.qaware.smartlab.microservice.service.connector.action;

import de.qaware.smartlab.api.service.client.action.IActionApiClient;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.exception.SmartLabException;
import de.qaware.smartlab.core.constant.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MICROSERVICE)
public class ActionMicroserviceConnector implements IActionService {

    private final IActionApiClient actionApiClient;

    public ActionMicroserviceConnector(IActionApiClient actionApiClient) {
        this.actionApiClient = actionApiClient;
    }

    @Override
    public IActionResult executeAction(String actionId, IActionArgs actionArgs) {
        try {
            ResponseEntity<IActionResult> response = this.actionApiClient.executeAction(actionId, actionArgs);
            return response.getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_ACTION_SERVICE_BASE_URL_GETTER)
    @ConditionalOnProperty(
            prefix = ModularityConfiguration.Properties.PREFIX,
            name = ModularityConfiguration.Properties.MODULARITY,
            havingValue = ModularityConfiguration.Properties.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IActionApiClient actionApiClient;

        public BaseUrlGetter(IActionApiClient actionApiClient) {
            this.actionApiClient = actionApiClient;
        }

        @Override
        public URL getBaseUrl() {
            try {
                return this.actionApiClient.getBaseUrl().getBody();
            }
            // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
            catch (Exception e) {
                throw new SmartLabException(e);
            }
        }
    }
}
