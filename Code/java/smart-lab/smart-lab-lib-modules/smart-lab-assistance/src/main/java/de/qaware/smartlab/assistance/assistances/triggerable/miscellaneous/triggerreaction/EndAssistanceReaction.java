package de.qaware.smartlab.assistance.assistances.triggerable.miscellaneous.triggerreaction;

import de.qaware.smartlab.api.service.connector.assistance.IAssistanceService;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.exception.context.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EndAssistanceReaction implements ITriggerReaction {

    @Override
    public void react(IAssistanceService assistanceService, IAssistanceContext context) {
        log.info("Trigger ends assistance \"{}\" for event \"{}\"",
                context.getAssistanceConfiguration().getAssistanceId(),
                context.getEvent().map(IEvent::getId).orElseThrow(InsufficientContextException::new));
        assistanceService.endAssistance(context);
    }
}
