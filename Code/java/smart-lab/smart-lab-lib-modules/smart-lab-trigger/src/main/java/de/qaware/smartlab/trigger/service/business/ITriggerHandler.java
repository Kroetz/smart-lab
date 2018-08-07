package de.qaware.smartlab.trigger.service.business;

import de.qaware.smartlab.assistance.assistances.triggerable.generic.IAssistanceTriggerable;
import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.event.IEvent;

import java.util.function.BiConsumer;

public interface ITriggerHandler {

    void triggerAssistances(
            IEvent event,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId);

    void triggerAssistance(
            IAssistanceConfiguration config,
            IEvent event,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId);
}
