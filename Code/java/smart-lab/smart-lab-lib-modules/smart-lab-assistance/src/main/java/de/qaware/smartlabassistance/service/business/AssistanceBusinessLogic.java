package de.qaware.smartlabassistance.service.business;

import de.qaware.smartlabapi.service.connector.action.IActionService;
import de.qaware.smartlabassistance.assistance.controllable.generic.IAssistanceControllable;
import de.qaware.smartlabassistance.assistance.controllable.miscellaneous.tracker.IAssistanceTracker;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.exception.InsufficientContextException;
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
        log.info("Executing begin stage of assistance (ID: \"{}\") at location with SERVICE_ID \"{}\"",
                assistanceId,
                context.getLocation().map(ILocation::getName).orElseThrow(InsufficientContextException::new));
        IAssistanceControllable assistance = this.assistanceTracker.track(context);
        assistance.begin(this.actionService, context);
        this.assistanceTracker.updateTracked(context, assistance);
        log.info("Executed begin stage of assistance (ID: \"{}\") at location with SERVICE_ID \"{}\"",
                assistanceId,
                context.getLocation().map(ILocation::getName).orElseThrow(InsufficientContextException::new));
    }

    public void endAssistance(String assistanceId, IAssistanceContext context) {
        log.info("Executing end stage of assistance (ID: \"{}\") at location with SERVICE_ID \"{}\"",
                assistanceId,
                context.getLocation().map(ILocation::getName).orElseThrow(InsufficientContextException::new));
        IAssistanceControllable assistance = this.assistanceTracker.getTracked(context);
        assistance.end(this.actionService, context);
        this.assistanceTracker.updateTracked(context, assistance);
        this.assistanceTracker.stopTracking(context);
        log.info("Executed end stage of assistance (ID: \"{}\") at location with SERVICE_ID \"{}\"",
                assistanceId,
                context.getLocation().map(ILocation::getName).orElseThrow(InsufficientContextException::new));
    }

    public void updateAssistance(String assistanceId, IAssistanceContext context) {

        // TODO: Implementation
    }
}
