package de.qaware.smartlab.assistance.assistances.triggerable.generic;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
public abstract class AbstractAssistanceTriggerable implements IAssistanceTriggerable {

    protected final IAssistanceInfo assistanceInfo;

    public AbstractAssistanceTriggerable(IAssistanceInfo assistanceInfo) {
        this.assistanceInfo = assistanceInfo;
    }

    public String getAssistanceId() {
        return this.assistanceInfo.getAssistanceId();
    }

    public Set<String> getAssistanceIdAliases() {
        return this.assistanceInfo.getAssistanceIdAliases();
    }

    public String getAssistanceCommand() {
        return this.assistanceInfo.getAssistanceCommand();
    }

    public Set<String> getAssistanceCommandAliases() {
        return this.assistanceInfo.getAssistanceCommandAliases();
    }

    @Override
    public IAssistanceConfiguration createConfiguration(Map<String, String> configProperties) {
        return this.assistanceInfo.createConfiguration(configProperties);
    }

    @Override
    public void reactOnTriggerSetUpMeeting(final IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Ignoring set-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerCleanUpMeeting(final IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Ignoring clean-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerStartMeeting(final IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Ignoring start-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerStopMeeting(final IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Ignoring stop-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }
}
