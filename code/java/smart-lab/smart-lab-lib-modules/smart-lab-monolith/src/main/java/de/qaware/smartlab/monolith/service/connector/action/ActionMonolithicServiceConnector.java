package de.qaware.smartlab.monolith.service.connector.action;

import de.qaware.smartlab.action.service.controller.ActionController;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.action.generic.IActionArgs;
import de.qaware.smartlab.core.action.generic.IActionResult;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.monolith.service.url.AbstractMonolithicBaseUrlGetter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MONOLITH)
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
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_ACTION_SERVICE_BASE_URL_GETTER)
    @ConditionalOnProperty(
            prefix = ModularityConfiguration.Properties.PREFIX,
            name = ModularityConfiguration.Properties.MODULARITY,
            havingValue = ModularityConfiguration.Properties.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(ActionController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
