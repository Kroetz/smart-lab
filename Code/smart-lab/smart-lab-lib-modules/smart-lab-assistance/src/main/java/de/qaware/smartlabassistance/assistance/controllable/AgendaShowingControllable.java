package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabaction.action.generic.IActionExecutable;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabassistance.assistance.info.AgendaShowingInfo;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AgendaShowingControllable extends AbstractAssistanceControllable {

    public AgendaShowingControllable(
            AgendaShowingInfo agendaShowingInfo,
            IResolver<String, IActionExecutable> actionResolver) {
        super(agendaShowingInfo, actionResolver);
    }

    @Override
    public void begin(IActionService actionService, IContext context) {
        // TODO: Implementation
    }

    @Override
    public void end(IActionService actionService, IContext context) {
        // TODO: Implementation
    }

    @Override
    public void update(IActionService actionService, IContext context) {
        // TODO: Implementation
    }
}
