package de.qaware.smartlab.gui.service.business;

import de.qaware.smartlab.api.service.constant.location.LocationManagementApiConstants;
import de.qaware.smartlab.api.service.constant.trigger.TriggerApiConstants;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.api.service.connector.location.ILocationManagementService;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;

import static java.lang.String.format;

@Component
@Slf4j
public class GuiBusinessLogic implements IGuiBusinessLogic {

    private final ILocationManagementService locationManagementService;
    private final IServiceBaseUrlGetter triggerServiceBaseUrlGetter;
    private final Duration locationStatusMeetingExtension;

    public GuiBusinessLogic(
            ILocationManagementService locationManagementService,
            // TODO: String literal
            @Qualifier("triggerServiceBaseUrlGetter") IServiceBaseUrlGetter triggerServiceBaseUrlGetter,
            @Qualifier("locationStatusMeetingExtension") Duration locationStatusMeetingExtension) {
        this.locationManagementService = locationManagementService;
        this.triggerServiceBaseUrlGetter = triggerServiceBaseUrlGetter;
        this.locationStatusMeetingExtension = locationStatusMeetingExtension;
    }

    @Override
    public String getCurrentMeetingStatusPage(LocationId locationId, Model model) {
        URL triggerServiceBaseUrl = this.triggerServiceBaseUrlGetter.getBaseUrl();
        // TODO: String literals
        model.addAttribute("locationId", locationId.getIdValue());
        IMeeting currentMeeting;
        try {
            currentMeeting = this.locationManagementService.getCurrentMeeting(locationId);
        }
        catch(EntityNotFoundException e) {
            return "meeting-status-not-available";
        }
        model.addAttribute("meetingTopic", currentMeeting.getTitle());
        model.addAttribute("minutesLeft", Duration.between(Instant.now(), currentMeeting.getEnd()).toMinutes());
        model.addAttribute("secondsLeft", Duration.between(Instant.now(), currentMeeting.getEnd()).getSeconds());
        model.addAttribute("startMeetingUrl", triggerServiceBaseUrl + format(TriggerApiConstants.URL_TEMPLATE_START_CURRENT_MEETING_BY_LOCATION_ID, locationId.getIdValue()));
        model.addAttribute("stopMeetingUrl", triggerServiceBaseUrl + format(TriggerApiConstants.URL_TEMPLATE_STOP_CURRENT_MEETING_BY_LOCATION_ID, locationId.getIdValue()));
        model.addAttribute("extendMeetingUrl", triggerServiceBaseUrl + format(LocationManagementApiConstants.URL_TEMPLATE_EXTEND_CURRENT_MEETING, locationId.getIdValue(), this.locationStatusMeetingExtension.toMinutes()));
        // TODO: Incorporate meeting details in page.
        return "meeting-status";
    }

    @Override
    public String getCurrentMeetingAgendaPage(LocationId locationId, Model model) {
        // TODO: String literals
        IMeeting currentMeeting;
        try {
            currentMeeting = this.locationManagementService.getCurrentMeeting(locationId);
        }
        catch(EntityNotFoundException e) {
            model.addAttribute("locationId", locationId.getIdValue());
            return "meeting-agenda-not-available";
        }
        model.addAttribute("meetingId", currentMeeting.getId().getIdValue());
        model.addAttribute("meetingTopic", currentMeeting.getTitle());
        model.addAttribute("agendaItems", currentMeeting.getAgenda());
        return "meeting-agenda";
    }
}
