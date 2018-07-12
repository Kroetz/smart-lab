package de.qaware.smartlabassistance.assistance.triggerable;

import de.qaware.smartlabapi.service.connector.assistance.IAssistanceService;
import de.qaware.smartlabassistance.assistance.info.MinuteTakingInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MinuteTakingTriggerable extends AbstractAssistanceTriggerable {

    public MinuteTakingTriggerable(MinuteTakingInfo minuteTakingInfo) {
        super(minuteTakingInfo);
    }

    @Override
    public void reactOnTriggerStartMeeting(IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Reaction on start-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to begin the assistance",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        assistanceService.beginAssistance(this.assistanceInfo.getAssistanceId(), context);
    }

    @Override
    public void reactOnTriggerStopMeeting(IAssistanceService assistanceService, final IAssistanceContext context) {
        log.info("Reaction on stop-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to end the assistance",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        assistanceService.endAssistance(this.assistanceInfo.getAssistanceId(), context);
    }
}
