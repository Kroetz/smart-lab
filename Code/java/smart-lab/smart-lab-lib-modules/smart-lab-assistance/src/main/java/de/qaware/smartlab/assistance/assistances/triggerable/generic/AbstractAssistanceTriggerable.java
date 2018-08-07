package de.qaware.smartlab.assistance.assistances.triggerable.generic;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.exception.InsufficientContextException;
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
    public void reactOnTriggerSetUpEvent(final IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Ignoring set-up-event trigger for assistance \"{}\" of event with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getEvent().map(IEvent::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerCleanUpEvent(final IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Ignoring clean-up-event trigger for assistance \"{}\" of event with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getEvent().map(IEvent::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerStartEvent(final IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Ignoring start-event trigger for assistance \"{}\" of event with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getEvent().map(IEvent::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerStopEvent(final IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Ignoring stop-event trigger for assistance \"{}\" of event with ID \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getEvent().map(IEvent::getId).orElseThrow(InsufficientContextException::new));
    }
}
