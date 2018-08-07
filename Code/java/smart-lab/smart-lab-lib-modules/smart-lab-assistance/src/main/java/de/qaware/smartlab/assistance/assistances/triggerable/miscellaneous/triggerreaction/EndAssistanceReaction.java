package de.qaware.smartlab.assistance.assistances.triggerable.miscellaneous.triggerreaction;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.exception.InsufficientContextException;
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
