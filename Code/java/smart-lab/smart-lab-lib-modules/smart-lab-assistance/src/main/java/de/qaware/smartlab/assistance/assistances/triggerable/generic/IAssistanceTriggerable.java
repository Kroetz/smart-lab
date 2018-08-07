package de.qaware.smartlab.assistance.assistances.triggerable.generic;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlab.assistance.assistances.generic.IAssistanceCommandSupplier;
import de.qaware.smartlab.assistance.assistances.generic.IAssistanceConfigurationSupplier;
import de.qaware.smartlab.assistance.assistances.generic.IAssistanceIdSupplier;
import de.qaware.smartlab.core.data.context.IAssistanceContext;

public interface IAssistanceTriggerable extends IAssistanceIdSupplier, IAssistanceCommandSupplier, IAssistanceConfigurationSupplier {

    void reactOnTriggerSetUpMeeting(IAssistanceService assistanceService, final IAssistanceContext context);
    void reactOnTriggerCleanUpMeeting(IAssistanceService assistanceService, final IAssistanceContext context);
    void reactOnTriggerStartMeeting(IAssistanceService assistanceService, final IAssistanceContext context);
    void reactOnTriggerStopMeeting(IAssistanceService assistanceService, final IAssistanceContext context);
}
