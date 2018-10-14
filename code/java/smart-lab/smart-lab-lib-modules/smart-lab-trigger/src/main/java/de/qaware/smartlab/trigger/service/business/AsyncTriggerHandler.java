package de.qaware.smartlab.trigger.service.business;

import de.qaware.smartlab.assistance.assistances.triggerable.generic.IAssistanceTriggerable;
import de.qaware.smartlab.core.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.event.IEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
@Slf4j
public class AsyncTriggerHandler implements ITriggerHandler {

    private final ITriggerHandler syncTriggerHandler;

    public AsyncTriggerHandler(ITriggerHandler syncTriggerHandler) {
        this.syncTriggerHandler = syncTriggerHandler;
    }

    @Override
    @Async
    public void triggerAssistances(
            IEvent event,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId) {
        this.syncTriggerHandler.triggerAssistances(event, triggerReaction, jobId);
    }

    @Override
    @Async
    public void triggerAssistance(
            IAssistanceConfiguration config,
            IEvent event,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId) {
        this.syncTriggerHandler.triggerAssistance(config, event, triggerReaction, jobId);
    }
}
