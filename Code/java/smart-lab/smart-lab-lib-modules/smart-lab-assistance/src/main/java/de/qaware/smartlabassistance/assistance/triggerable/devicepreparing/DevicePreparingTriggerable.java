package de.qaware.smartlabassistance.assistance.triggerable.devicepreparing;

import de.qaware.smartlabapi.service.connector.assistance.IAssistanceService;
import de.qaware.smartlabassistance.assistance.info.devicepreparing.DevicePreparingInfo;
import de.qaware.smartlabassistance.assistance.triggerable.generic.AbstractAssistanceTriggerable;
import de.qaware.smartlabassistance.assistance.triggerable.miscellaneous.triggerreaction.ITriggerReaction;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DevicePreparingTriggerable extends AbstractAssistanceTriggerable {

    private final ITriggerReaction beginAssistanceReaction;
    private final ITriggerReaction endAssistanceReaction;

    public DevicePreparingTriggerable(
            DevicePreparingInfo devicePreparingInfo,
            ITriggerReaction beginAssistanceReaction,
            ITriggerReaction endAssistanceReaction) {
        super(devicePreparingInfo);
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
