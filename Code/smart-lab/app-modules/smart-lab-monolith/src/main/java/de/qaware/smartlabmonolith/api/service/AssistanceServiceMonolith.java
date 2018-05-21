package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabassistance.controller.AssistanceController;
import de.qaware.smartlabcommons.api.internal.service.assistance.IAssistanceService;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(ProfileNames.MONOLITH)
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
