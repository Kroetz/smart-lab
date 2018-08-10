package de.qaware.smartlab.trigger.provider.cleanupevent;

import de.qaware.smartlab.api.service.connector.event.IEventManagementService;
import de.qaware.smartlab.api.service.connector.trigger.ITriggerService;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.trigger.configuration.CleanUpEventTriggerProviderConfiguration;
import de.qaware.smartlab.trigger.provider.generic.AbstractTriggerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

import static de.qaware.smartlab.core.miscellaneous.TimeUtils.isBetween;
import static java.util.stream.Collectors.toSet;

@Component
@Slf4j
public class CleanUpEventTriggerProvider extends AbstractTriggerProvider {

    private static final String TRIGGER_NAME = "clean up event";

    private final IEventManagementService eventManagementService;
    private final Duration triggerThreshold;

    public CleanUpEventTriggerProvider(
            ITriggerService triggerService,
            IEventManagementService eventManagementService,
            @Qualifier(CleanUpEventTriggerProviderConfiguration.QUALIFIER_CLEAN_UP_TRIGGER_PROVIDER_CHECK_INTERVAL) Duration checkInterval,
            @Qualifier(CleanUpEventTriggerProviderConfiguration.QUALIFIER_CLEAN_UP_TRIGGER_PROVIDER_TRIGGER_THRESHOLD) Duration triggerThreshold,
            @Qualifier(CleanUpEventTriggerProviderConfiguration.QUALIFIER_CLEAN_UP_TRIGGER_PROVIDER_CALLBACK_URL) URL callbackUrl) {
        super(
                checkInterval,
                event -> triggerService.cleanUpCurrentEventByLocationId(event.getLocationId(), callbackUrl),
                TRIGGER_NAME);
        this.eventManagementService = eventManagementService;
        this.triggerThreshold = triggerThreshold;
    }

    @Override
    protected Set<IEvent> findTriggerCandidates() {
        return eventManagementService.findAllCurrent().stream()
                .filter(this::isAboutToEnd)
                .collect(toSet());
    }

    private boolean isAboutToEnd(IEvent event) {
        return isBetween(
                this.currentCheck,
                event.getEnd().minus(this.triggerThreshold),
                event.getEnd());
    }
}
