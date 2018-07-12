package de.qaware.smartlabapi.service.connector.action;

import de.qaware.smartlabapi.service.client.action.IActionApiClient;
import de.qaware.smartlabapi.service.url.AbstractMicroserviceBaseUrlGetter;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
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
        } catch (FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Component
    // TODO: String literal
    @Qualifier("actionServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    public static class BaseUrlGetter extends AbstractMicroserviceBaseUrlGetter {

        public BaseUrlGetter(IActionApiClient actionApiClient) {
            super(actionApiClient);
        }
    }
}
