package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabaction.controller.ActionController;
import de.qaware.smartlabcommons.api.internal.service.action.IActionService;
import de.qaware.smartlabaction.action.generic.IActionArgs;
import de.qaware.smartlabaction.action.generic.result.IActionResult;
import de.qaware.smartlabcommons.miscellaneous.Property;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
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
