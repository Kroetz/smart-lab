package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabassistance.assistance.triggerable.IAssistanceTriggerable;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.meeting.IMeeting;

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
