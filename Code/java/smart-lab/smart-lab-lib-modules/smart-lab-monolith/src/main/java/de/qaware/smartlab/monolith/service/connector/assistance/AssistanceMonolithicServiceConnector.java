package de.qaware.smartlab.monolith.service.connector.assistance;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlab.assistance.service.controller.AssistanceController;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.constant.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.monolith.service.url.AbstractMonolithicBaseUrlGetter;
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
    public void beginAssistance(IAssistanceContext context) {
        this.assistanceController.beginAssistance(context);
    }

    @Override
    public void endAssistance(IAssistanceContext context) {
        this.assistanceController.endAssistance(context);
    }

    @Override
    public void duringAssistance(IAssistanceContext context) {
        this.assistanceController.duringAssistance(context);
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_ASSISTANCE_SERVICE_BASE_URL_GETTER)
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
