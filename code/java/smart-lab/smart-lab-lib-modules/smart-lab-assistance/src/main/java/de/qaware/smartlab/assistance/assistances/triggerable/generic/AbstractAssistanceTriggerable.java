package de.qaware.smartlab.assistance.assistances.triggerable.generic;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
public abstract class AbstractAssistanceTriggerable implements IAssistanceTriggerable {

    protected final IAssistanceInfo assistanceInfo;

    protected AbstractAssistanceTriggerable(IAssistanceInfo assistanceInfo) {
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
        log.info("Ignoring set-up-event trigger of assistance \"{}\" for event \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getEvent().getId());
    }

    @Override
    public void reactOnTriggerCleanUpEvent(final IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Ignoring clean-up-event trigger of assistance \"{}\" for event \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getEvent().getId());
    }

    @Override
    public void reactOnTriggerStartEvent(final IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Ignoring start-event trigger of assistance \"{}\" for event \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getEvent().getId());
    }

    @Override
    public void reactOnTriggerStopEvent(final IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Ignoring stop-event trigger of assistance \"{}\" for event \"{}\"",
                this.assistanceInfo.getAssistanceId(),
                context.getEvent().getId());
    }
}
