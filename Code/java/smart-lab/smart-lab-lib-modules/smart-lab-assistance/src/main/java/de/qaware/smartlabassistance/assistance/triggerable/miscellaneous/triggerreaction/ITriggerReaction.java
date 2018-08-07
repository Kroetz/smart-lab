package de.qaware.smartlabassistance.assistance.triggerable.miscellaneous.triggerreaction;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlabcore.data.context.IAssistanceContext;

public interface ITriggerReaction {

    void react(IAssistanceService assistanceService, String assistanceId, IAssistanceContext context);
}
