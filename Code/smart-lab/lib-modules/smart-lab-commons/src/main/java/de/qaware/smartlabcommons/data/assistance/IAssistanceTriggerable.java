package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.internal.service.assistance.IAssistanceService;
import de.qaware.smartlabcommons.data.context.IContext;

public interface IAssistanceTriggerable extends IAssistance {

    void reactOnTriggerSetUpMeeting(IAssistanceService assistanceService, final IContext context);
    void reactOnTriggerCleanUpMeeting(IAssistanceService assistanceService, final IContext context);
    void reactOnTriggerStartMeeting(IAssistanceService assistanceService, final IContext context);
    void reactOnTriggerStopMeeting(IAssistanceService assistanceService, final IContext context);
}
