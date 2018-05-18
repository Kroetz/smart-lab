package de.qaware.smartlabcommons.api.service.action;

import de.qaware.smartlabcommons.api.client.IActionApiClient;
import de.qaware.smartlabcommons.data.action.ActionResult;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.IActionResult;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import feign.FeignException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Profile(ProfileNames.MICROSERVICE)
public class ActionMicroservice implements IActionService {

    private final IActionApiClient actionApiClient;

    public ActionMicroservice(IActionApiClient actionApiClient) {
        this.actionApiClient = actionApiClient;
    }

    @Override
    public IActionResult executeAction(String actionId, IActionArgs actionArgs) {
        try {
            ResponseEntity<ActionResult> response = this.actionApiClient.executeAction(actionId, actionArgs);
            return response.getBody();
        } catch (FeignException e) {
            throw new UnknownErrorException();
        }
    }
}
