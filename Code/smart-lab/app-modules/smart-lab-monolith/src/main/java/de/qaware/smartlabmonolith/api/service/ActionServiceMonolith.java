package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabaction.controller.ActionController;
import de.qaware.smartlabcommons.api.service.action.IActionService;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.result.IActionResult;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Profile(ProfileNames.MONOLITH)
public class ActionServiceMonolith implements IActionService {

    private final ActionController actionController;

    public ActionServiceMonolith(ActionController actionController) {
        this.actionController = actionController;
    }

    @Override
    public IActionResult executeAction(String actionId, IActionArgs actionArgs) {
        ResponseEntity<IActionResult> response = this.actionController.executeAction(actionId, actionArgs);
        return response.getBody();
    }
}
