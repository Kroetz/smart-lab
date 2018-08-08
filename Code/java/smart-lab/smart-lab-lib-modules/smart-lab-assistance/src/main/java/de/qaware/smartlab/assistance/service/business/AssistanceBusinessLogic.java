package de.qaware.smartlab.assistance.service.business;

import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.tracker.IAssistanceTracker;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.exception.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssistanceBusinessLogic implements IAssistanceBusinessLogic {

    private final IActionService actionService;
    private final IAssistanceTracker assistanceTracker;

    public AssistanceBusinessLogic(
            IActionService actionService,
            IAssistanceTracker assistanceTracker) {
        this.actionService = actionService;
        this.assistanceTracker = assistanceTracker;
    }

    public void beginAssistance(String assistanceId, final IAssistanceContext context) {
        log.info("Executing stage \"begin\" of assistance (ID: \"{}\") at location with ID \"{}\"",
                assistanceId,
                context.getLocation().map(ILocation::getName).orElseThrow(InsufficientContextException::new));
        IAssistanceControllable assistance = this.assistanceTracker.track(context);
        assistance.begin(this.actionService, context);
        this.assistanceTracker.updateTracked(context, assistance);
        log.info("Executed stage \"begin\" of assistance (ID: \"{}\") at location with ID \"{}\"",
                assistanceId,
                context.getLocation().map(ILocation::getName).orElseThrow(InsufficientContextException::new));
    }

    public void endAssistance(String assistanceId, IAssistanceContext context) {
        log.info("Executing stage \"end\" of assistance (ID: \"{}\") at location with ID \"{}\"",
                assistanceId,
                context.getLocation().map(ILocation::getName).orElseThrow(InsufficientContextException::new));
        IAssistanceControllable assistance = this.assistanceTracker.getTracked(context);
        assistance.end(this.actionService, context);
        this.assistanceTracker.updateTracked(context, assistance);
        this.assistanceTracker.stopTracking(context);
        log.info("Executed stage \"end\" of assistance (ID: \"{}\") at location with ID \"{}\"",
                assistanceId,
                context.getLocation().map(ILocation::getName).orElseThrow(InsufficientContextException::new));
    }

    public void duringAssistance(String assistanceId, IAssistanceContext context) {
        log.info("Executing stage \"during\" of assistance (ID: \"{}\") at location with ID \"{}\"",
                assistanceId,
                context.getLocation().map(ILocation::getName).orElseThrow(InsufficientContextException::new));
        IAssistanceControllable assistance = this.assistanceTracker.getTracked(context);
        assistance.during(this.actionService, context);
        this.assistanceTracker.updateTracked(context, assistance);
        log.info("Executed stage \"during\" of assistance (ID: \"{}\") at location with ID \"{}\"",
                assistanceId,
                context.getLocation().map(ILocation::getName).orElseThrow(InsufficientContextException::new));
    }
}
