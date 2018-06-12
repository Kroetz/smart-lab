package de.qaware.smartlabassistance.assistance.triggerable;

import de.qaware.smartlabapi.service.assistance.IAssistanceService;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public abstract class AbstractAssistanceTriggerable implements IAssistanceTriggerable, IAssistanceInfo {

    protected final IAssistanceInfo assistanceInfo;

    public AbstractAssistanceTriggerable(IAssistanceInfo assistanceInfo) {
        this.assistanceInfo = assistanceInfo;
    }

    public String getAssistanceId() {
        return this.assistanceInfo.getAssistanceId();
    }

    public Set<String> getAssistanceAliases() {
        return this.assistanceInfo.getAssistanceAliases();
    }

    @Override
    public void reactOnTriggerSetUpMeeting(final IAssistanceService assistanceService, final IContext context) {
        log.info("Ignoring set-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerCleanUpMeeting(final IAssistanceService assistanceService, final IContext context) {
        log.info("Ignoring clean-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerStartMeeting(final IAssistanceService assistanceService, final IContext context) {
        log.info("Ignoring start-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerStopMeeting(final IAssistanceService assistanceService, final IContext context) {
        log.info("Ignoring stop-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }
}
