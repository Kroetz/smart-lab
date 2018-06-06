package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabassistance.assistance.IAssistanceTriggerable;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.meeting.IMeeting;

import java.util.function.BiConsumer;

public interface ITriggerHandler {

    void triggerAssistances(
            IMeeting meeting,
            BiConsumer<IContext, IAssistanceTriggerable> triggerReactionGetter,
            Long jobId);

    void triggerAssistance(
            String assistanceId,
            IMeeting meeting,
            BiConsumer<IContext, IAssistanceTriggerable> triggerReactionGetter,
            Long jobId);
}
