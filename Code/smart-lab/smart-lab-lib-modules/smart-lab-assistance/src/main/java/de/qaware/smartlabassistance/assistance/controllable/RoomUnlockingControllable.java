package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabassistance.assistance.controllable.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class RoomUnlockingControllable extends AbstractAssistanceControllable {

    private RoomUnlockingControllable(IAssistanceInfo roomUnlockingInfo) {
        super(roomUnlockingInfo);
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        // TODO: Implementation
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        // TODO: Implementation
    }

    @Override
    public void update(IActionService actionService, IAssistanceContext context) {
        // TODO: Implementation
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        public Factory(IAssistanceInfo roomUnlockingInfo) {
            super(roomUnlockingInfo);
        }

        @Override
        public IAssistanceControllable newInstance() {
            return new RoomUnlockingControllable(this.assistanceInfo);
        }
    }
}
