package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabaction.controller.ActionController;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.url.AbstractMonolithicBaseUrlGetter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class ActionMonolithicService implements IActionService {

    private final ActionController actionController;

    public ActionMonolithicService(ActionController actionController) {
        this.actionController = actionController;
    }

    @Override
    public IActionResult executeAction(String actionId, IActionArgs actionArgs) {
        ResponseEntity<IActionResult> response = this.actionController.executeAction(actionId, actionArgs);
        return response.getBody();
    }

    @Component
    // TODO: String literal
    @Qualifier("actionServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(ActionController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
