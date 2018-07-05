package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabassistance.controller.AssistanceController;
import de.qaware.smartlabapi.service.assistance.IAssistanceService;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.miscellaneous.Property;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class AssistanceMonolithicService implements IAssistanceService {

    private final AssistanceController assistanceController;

    public AssistanceMonolithicService(AssistanceController assistanceController) {
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
}
