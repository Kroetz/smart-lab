package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabassistance.assistance.triggerable.IAssistanceTriggerable;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.meeting.IMeeting;
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
            BiConsumer<IContext, IAssistanceTriggerable> triggerReaction,
            Long jobId) {
        this.syncTriggerHandler.triggerAssistances(meeting, triggerReaction, jobId);
    }

    @Override
    @Async
    public void triggerAssistance(
            String assistanceId,
            IMeeting meeting,
            BiConsumer<IContext, IAssistanceTriggerable> triggerReaction,
            Long jobId) {
        this.syncTriggerHandler.triggerAssistance(assistanceId, meeting, triggerReaction, jobId);
    }
}
