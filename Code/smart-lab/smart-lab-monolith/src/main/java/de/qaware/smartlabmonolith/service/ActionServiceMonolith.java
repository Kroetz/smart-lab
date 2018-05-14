package de.qaware.smartlabmonolith.service;

import de.qaware.smartlabaction.controller.ActionController;
import de.qaware.smartlabcommons.api.service.action.IActionService;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Constants.PROFILE_NAME_MONOLITH)
public class ActionServiceMonolith implements IActionService {

    private final ActionController actionController;

    public ActionServiceMonolith(ActionController actionController) {
        this.actionController = actionController;
    }

    @Override
    public void executeAction(String actionId, IActionArgs actionArgs) {
        this.actionController.executeAction(actionId, actionArgs);
    }
}
