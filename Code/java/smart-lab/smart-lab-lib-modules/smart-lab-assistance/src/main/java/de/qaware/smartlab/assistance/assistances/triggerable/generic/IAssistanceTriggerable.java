package de.qaware.smartlab.assistance.assistances.triggerable.generic;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlab.assistance.assistances.generic.IAssistanceCommandSupplier;
import de.qaware.smartlab.assistance.assistances.generic.IAssistanceConfigurationSupplier;
import de.qaware.smartlab.assistance.assistances.generic.IAssistanceIdSupplier;
import de.qaware.smartlab.core.data.context.IAssistanceContext;

public interface IAssistanceTriggerable extends IAssistanceIdSupplier, IAssistanceCommandSupplier, IAssistanceConfigurationSupplier {

    void reactOnTriggerSetUpEvent(IAssistanceService assistanceService, final IAssistanceContext context);
    void reactOnTriggerCleanUpEvent(IAssistanceService assistanceService, final IAssistanceContext context);
    void reactOnTriggerStartEvent(IAssistanceService assistanceService, final IAssistanceContext context);
    void reactOnTriggerStopEvent(IAssistanceService assistanceService, final IAssistanceContext context);
}
