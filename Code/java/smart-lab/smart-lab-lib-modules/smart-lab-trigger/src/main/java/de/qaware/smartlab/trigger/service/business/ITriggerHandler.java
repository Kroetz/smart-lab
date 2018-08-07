package de.qaware.smartlab.trigger.service.business;

import de.qaware.smartlab.assistance.assistances.triggerable.generic.IAssistanceTriggerable;
import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.meeting.IMeeting;

import java.util.function.BiConsumer;

public interface ITriggerHandler {

    void triggerAssistances(
            IMeeting meeting,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId);

    void triggerAssistance(
            IAssistanceConfiguration config,
            IMeeting meeting,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId);
}
