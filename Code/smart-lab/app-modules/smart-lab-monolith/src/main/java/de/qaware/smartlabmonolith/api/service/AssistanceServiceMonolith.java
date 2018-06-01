package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabassistance.controller.AssistanceController;
import de.qaware.smartlabapi.service.assistance.IAssistanceService;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.miscellaneous.Property;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class AssistanceServiceMonolith implements IAssistanceService {

    private final AssistanceController assistanceController;

    public AssistanceServiceMonolith(AssistanceController assistanceController) {
        this.assistanceController = assistanceController;
    }

    @Override
    public void beginAssistance(String assistanceId, IContext context) {
        this.assistanceController.beginAssistance(assistanceId, context);
    }

    @Override
    public void endAssistance(String assistanceId, IContext context) {
        this.assistanceController.endAssistance(assistanceId, context);
    }

    @Override
    public void updateAssistance(String assistanceId, IContext context) {
        this.assistanceController.updateAssistance(assistanceId, context);
    }
}
