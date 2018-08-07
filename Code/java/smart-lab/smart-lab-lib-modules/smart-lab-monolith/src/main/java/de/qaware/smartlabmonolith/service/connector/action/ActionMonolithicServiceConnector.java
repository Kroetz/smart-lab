package de.qaware.smartlabmonolith.service.connector.action;

import de.qaware.smartlab.action.service.controller.ActionController;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.service.url.AbstractMonolithicBaseUrlGetter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class ActionMonolithicServiceConnector implements IActionService {

    private final ActionController actionController;

    public ActionMonolithicServiceConnector(ActionController actionController) {
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
