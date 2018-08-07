package de.qaware.smartlab.assistance.assistances.controllable.locationunlocking;

import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.assistance.assistances.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class LocationUnlockingControllable extends AbstractAssistanceControllable {

    private LocationUnlockingControllable(IAssistanceInfo locationUnlockingInfo) {
        super(locationUnlockingInfo);
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

        public Factory(IAssistanceInfo locationUnlockingInfo) {
            super(locationUnlockingInfo);
        }

        @Override
        public IAssistanceControllable newInstance() {
            return new LocationUnlockingControllable(this.assistanceInfo);
        }
    }
}
