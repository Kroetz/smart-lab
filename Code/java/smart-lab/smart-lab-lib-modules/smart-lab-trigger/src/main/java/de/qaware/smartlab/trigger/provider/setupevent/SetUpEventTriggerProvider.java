package de.qaware.smartlab.trigger.provider.setupevent;

import de.qaware.smartlab.api.service.connector.event.IEventManagementService;
import de.qaware.smartlab.api.service.connector.trigger.ITriggerService;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.trigger.provider.generic.AbstractTriggerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

@Component
@Slf4j
public class SetUpEventTriggerProvider extends AbstractTriggerProvider {

    private static final String TRIGGER_NAME = "set up event";

    private final IEventManagementService eventManagementService;

    public SetUpEventTriggerProvider(
            IEventManagementService eventManagementService,
            ITriggerService triggerService,
            // TODO: String literals
            @Qualifier("setUpTriggerProviderCheckInterval") Duration checkInterval,
            // TODO: String literal
            @Qualifier("setUpTriggerProviderCallbackUrl") URL callbackUrl) {
        super(
                checkInterval,
                event -> triggerService.setUpCurrentEventByLocationId(event.getLocationId(), callbackUrl),
                TRIGGER_NAME);
        this.eventManagementService = eventManagementService;
    }

    @Override
    protected Set<IEvent> findTriggerCandidates() {
        return eventManagementService.findAllCurrent();
    }
}
