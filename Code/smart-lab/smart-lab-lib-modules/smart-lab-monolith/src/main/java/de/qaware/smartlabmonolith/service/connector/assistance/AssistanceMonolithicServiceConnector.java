package de.qaware.smartlabmonolith.service.connector.assistance;

import de.qaware.smartlabapi.service.connector.assistance.IAssistanceService;
import de.qaware.smartlabassistance.service.controller.AssistanceController;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.url.AbstractMonolithicBaseUrlGetter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class AssistanceMonolithicServiceConnector implements IAssistanceService {

    private final AssistanceController assistanceController;

    public AssistanceMonolithicServiceConnector(AssistanceController assistanceController) {
        this.assistanceController = assistanceController;
    }

    @Override
    public void beginAssistance(String assistanceId, IAssistanceContext context) {
        this.assistanceController.beginAssistance(assistanceId, context);
    }

    @Override
    public void endAssistance(String assistanceId, IAssistanceContext context) {
        this.assistanceController.endAssistance(assistanceId, context);
    }

    @Override
    public void updateAssistance(String assistanceId, IAssistanceContext context) {
        this.assistanceController.updateAssistance(assistanceId, context);
    }

    @Component
    // TODO: String literal
    @Qualifier("assistanceServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(AssistanceController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
