package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabaction.action.executable.IActionExecutable;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabassistance.assistance.info.RoomUnlockingInfo;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoomUnlockingControllable extends AbstractAssistanceControllable {

    public RoomUnlockingControllable(
            RoomUnlockingInfo roomUnlockingInfo,
            IResolver<String, IActionExecutable> actionResolver) {
        super(roomUnlockingInfo, actionResolver);
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
