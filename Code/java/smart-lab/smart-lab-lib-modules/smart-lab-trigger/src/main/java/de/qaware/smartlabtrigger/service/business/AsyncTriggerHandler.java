package de.qaware.smartlabtrigger.service.business;

import de.qaware.smartlab.assistance.assistances.triggerable.generic.IAssistanceTriggerable;
import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.meeting.IMeeting;
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
            IMeeting meeting,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId) {
        this.syncTriggerHandler.triggerAssistances(meeting, triggerReaction, jobId);
    }

    @Override
    @Async
    public void triggerAssistance(
            IAssistanceConfiguration config,
            IMeeting meeting,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId) {
        this.syncTriggerHandler.triggerAssistance(config, meeting, triggerReaction, jobId);
    }
}
