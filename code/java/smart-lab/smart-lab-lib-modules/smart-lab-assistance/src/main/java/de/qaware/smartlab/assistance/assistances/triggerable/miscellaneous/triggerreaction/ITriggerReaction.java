package de.qaware.smartlab.assistance.assistances.triggerable.miscellaneous.triggerreaction;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlab.core.data.context.IAssistanceContext;

public interface ITriggerReaction {

    void react(IAssistanceService assistanceService, IAssistanceContext context);
}
