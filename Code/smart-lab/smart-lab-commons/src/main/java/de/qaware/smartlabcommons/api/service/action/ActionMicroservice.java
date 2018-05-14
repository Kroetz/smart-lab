package de.qaware.smartlabcommons.api.service.action;

import de.qaware.smartlabcommons.api.client.IActionApiClient;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import feign.FeignException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Constants.PROFILE_NAME_MICROSERVICE)
public class ActionMicroservice implements IActionService {

    private final IActionApiClient actionApiClient;

    public ActionMicroservice(IActionApiClient actionApiClient) {
        this.actionApiClient = actionApiClient;
    }

    @Override
    public void executeAction(String actionId, IActionArgs actionArgs) {
        try {
            this.actionApiClient.executeAction(actionId, actionArgs);
        } catch (FeignException e) {
            throw new UnknownErrorException();
        }
    }
}
