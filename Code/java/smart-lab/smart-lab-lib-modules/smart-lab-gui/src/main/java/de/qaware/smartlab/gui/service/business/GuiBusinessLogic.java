package de.qaware.smartlab.gui.service.business;

import de.qaware.smartlab.api.service.constant.location.LocationManagementApiConstants;
import de.qaware.smartlab.api.service.constant.trigger.TriggerApiConstants;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.api.service.connector.location.ILocationManagementService;
import de.qaware.smartlab.core.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;

import static java.lang.String.format;
import static java.time.Duration.between;

@Component
@Slf4j
public class GuiBusinessLogic implements IGuiBusinessLogic {

    private final ILocationManagementService locationManagementService;
    private final IServiceBaseUrlGetter triggerServiceBaseUrlGetter;
    private final Duration locationStatusEventExtension;

    public GuiBusinessLogic(
            ILocationManagementService locationManagementService,
            // TODO: String literal
            @Qualifier(IServiceBaseUrlGetter.QUALIFIER_TRIGGER_SERVICE_BASE_URL_GETTER) IServiceBaseUrlGetter triggerServiceBaseUrlGetter,
            @Qualifier("locationStatusEventExtension") Duration locationStatusEventExtension) {
        this.locationManagementService = locationManagementService;
        this.triggerServiceBaseUrlGetter = triggerServiceBaseUrlGetter;
        this.locationStatusEventExtension = locationStatusEventExtension;
    }

    @Override
    public String getCurrentEventStatusPage(LocationId locationId, Model model) {
        URL triggerServiceBaseUrl = this.triggerServiceBaseUrlGetter.getBaseUrl();
        // TODO: String literals
        model.addAttribute("locationId", locationId.getIdValue());
        IEvent currentEvent;
        try {
            currentEvent = this.locationManagementService.getCurrentEvent(locationId);
        }
        catch(EntityNotFoundException e) {
            return "event-status-not-available";
        }
        model.addAttribute("eventTopic", currentEvent.getTitle());
        model.addAttribute("minutesLeft", between(Instant.now(), currentEvent.getEnd()).toMinutes());
        model.addAttribute("secondsLeft", between(Instant.now(), currentEvent.getEnd()).getSeconds());
        model.addAttribute("startEventUrl", triggerServiceBaseUrl + format(TriggerApiConstants.URL_TEMPLATE_START_CURRENT_EVENT_BY_LOCATION_ID, locationId.getIdValue()));
        model.addAttribute("stopEventUrl", triggerServiceBaseUrl + format(TriggerApiConstants.URL_TEMPLATE_STOP_CURRENT_EVENT_BY_LOCATION_ID, locationId.getIdValue()));
        model.addAttribute("extendEventUrl", triggerServiceBaseUrl + format(LocationManagementApiConstants.URL_TEMPLATE_EXTEND_CURRENT_EVENT, locationId.getIdValue(), this.locationStatusEventExtension.toMinutes()));
        return "event-status";
    }

    @Override
    public String getCurrentEventAgendaPage(LocationId locationId, Model model) {
        // TODO: String literals
        IEvent currentEvent;
        try {
            currentEvent = this.locationManagementService.getCurrentEvent(locationId);
        }
        catch(EntityNotFoundException e) {
            model.addAttribute("locationId", locationId.getIdValue());
            return "event-agenda-not-available";
        }
        model.addAttribute("eventId", currentEvent.getId().getIdValue());
        model.addAttribute("eventTopic", currentEvent.getTitle());
        model.addAttribute("agendaItems", currentEvent.getAgenda());
        return "event-agenda";
    }
}
