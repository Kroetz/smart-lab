package de.qaware.smartlabcommons.api.service.action;

import de.qaware.smartlabcommons.api.client.IActionApiClient;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import feign.FeignException;
import org.springframework.stereotype.Component;

@Component
public class ActionService implements IActionService {

    private final IActionApiClient actionApiClient;

    public ActionService(IActionApiClient actionApiClient) {
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
