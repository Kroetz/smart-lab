package de.qaware.smartlabassistance.assistance.triggerable.generic;

import de.qaware.smartlabapi.service.connector.assistance.IAssistanceService;
import de.qaware.smartlabassistance.assistance.generic.IAssistanceCommandSupplier;
import de.qaware.smartlabassistance.assistance.generic.IAssistanceConfigurationSupplier;
import de.qaware.smartlabassistance.assistance.generic.IAssistanceIdSupplier;
import de.qaware.smartlabcore.data.context.IAssistanceContext;

public interface IAssistanceTriggerable extends IAssistanceIdSupplier, IAssistanceCommandSupplier, IAssistanceConfigurationSupplier {

    void reactOnTriggerSetUpMeeting(IAssistanceService assistanceService, final IAssistanceContext context);
    void reactOnTriggerCleanUpMeeting(IAssistanceService assistanceService, final IAssistanceContext context);
    void reactOnTriggerStartMeeting(IAssistanceService assistanceService, final IAssistanceContext context);
    void reactOnTriggerStopMeeting(IAssistanceService assistanceService, final IAssistanceContext context);
}
