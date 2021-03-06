package de.qaware.smartlab.assistance.assistances.triggerable.locationunlocking;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlab.assistance.assistances.info.locationunlocking.LocationUnlockingInfo;
import de.qaware.smartlab.assistance.assistances.triggerable.generic.AbstractAssistanceTriggerable;
import de.qaware.smartlab.assistance.assistances.triggerable.miscellaneous.triggerreaction.ITriggerReaction;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LocationUnlockingTriggerable extends AbstractAssistanceTriggerable {

    private final ITriggerReaction beginAssistanceReaction;
    private final ITriggerReaction endAssistanceReaction;

    public LocationUnlockingTriggerable(
            LocationUnlockingInfo locationUnlockingInfo,
            ITriggerReaction beginAssistanceReaction,
            ITriggerReaction endAssistanceReaction) {
        super(locationUnlockingInfo);
        this.beginAssistanceReaction = beginAssistanceReaction;
        this.endAssistanceReaction = endAssistanceReaction;
    }

    @Override
    public void reactOnTriggerSetUpEvent(IAssistanceService assistanceService, IAssistanceContext context) {
        this.beginAssistanceReaction.react(assistanceService, context);
    }

    @Override
    public void reactOnTriggerCleanUpEvent(IAssistanceService assistanceService, IAssistanceContext context) {
        this.endAssistanceReaction.react(assistanceService, context);
    }
}
