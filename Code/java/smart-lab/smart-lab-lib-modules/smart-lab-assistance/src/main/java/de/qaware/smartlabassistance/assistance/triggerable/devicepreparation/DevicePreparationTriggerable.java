package de.qaware.smartlabassistance.assistance.triggerable.devicepreparation;

import de.qaware.smartlabapi.service.connector.assistance.IAssistanceService;
import de.qaware.smartlabassistance.assistance.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlabassistance.assistance.triggerable.generic.AbstractAssistanceTriggerable;
import de.qaware.smartlabassistance.assistance.triggerable.miscellaneous.triggerreaction.ITriggerReaction;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DevicePreparationTriggerable extends AbstractAssistanceTriggerable {

    private final ITriggerReaction beginAssistanceReaction;
    private final ITriggerReaction endAssistanceReaction;

    public DevicePreparationTriggerable(
            DevicePreparationInfo devicePreparationInfo,
            ITriggerReaction beginAssistanceReaction,
            ITriggerReaction endAssistanceReaction) {
        super(devicePreparationInfo);
        this.beginAssistanceReaction = beginAssistanceReaction;
        this.endAssistanceReaction = endAssistanceReaction;
    }

    @Override
    public void reactOnTriggerSetUpMeeting(IAssistanceService assistanceService, IAssistanceContext context) {
        this.beginAssistanceReaction.react(assistanceService, this.assistanceInfo.getAssistanceId(), context);
    }

    @Override
    public void reactOnTriggerCleanUpMeeting(IAssistanceService assistanceService, IAssistanceContext context) {
        this.endAssistanceReaction.react(assistanceService, this.assistanceInfo.getAssistanceId(), context);
    }
}
