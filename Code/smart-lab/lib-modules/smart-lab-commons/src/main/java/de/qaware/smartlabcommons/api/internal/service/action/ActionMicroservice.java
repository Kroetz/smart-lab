package de.qaware.smartlabcommons.api.internal.service.action;

import de.qaware.smartlabcommons.api.internal.client.IActionApiClient;
import de.qaware.smartlabaction.action.IActionArgs;
import de.qaware.smartlabaction.action.generic.result.IActionResult;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import de.qaware.smartlabcommons.miscellaneous.Property;
import feign.FeignException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class ActionMicroservice implements IActionService {

    private final IActionApiClient actionApiClient;

    public ActionMicroservice(IActionApiClient actionApiClient) {
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
}
