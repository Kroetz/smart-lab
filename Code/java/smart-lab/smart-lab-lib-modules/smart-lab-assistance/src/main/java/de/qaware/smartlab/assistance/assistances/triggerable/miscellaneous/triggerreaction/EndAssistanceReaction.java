package de.qaware.smartlab.assistance.assistances.triggerable.miscellaneous.triggerreaction;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EndAssistanceReaction implements ITriggerReaction {

    @Override
    public void react(IAssistanceService assistanceService, String assistanceId, IAssistanceContext context) {
        log.info("Trigger ends assistance \"{}\" of meeting \"{}\"",
                assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        assistanceService.endAssistance(assistanceId, context);
    }
}
