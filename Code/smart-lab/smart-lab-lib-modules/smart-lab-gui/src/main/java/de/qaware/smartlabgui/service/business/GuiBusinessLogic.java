package de.qaware.smartlabgui.service.business;

import de.qaware.smartlabapi.service.constant.room.RoomManagementApiConstants;
import de.qaware.smartlabapi.service.constant.trigger.TriggerApiConstants;
import de.qaware.smartlabcore.url.IServiceBaseUrlGetter;
import de.qaware.smartlabapi.service.connector.room.IRoomManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.miscellaneous.Constants;
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

    private final IRoomManagementService roomManagementService;
    private final IServiceBaseUrlGetter triggerServiceBaseUrlGetter;
    private final Duration roomStatusMeetingExtension;

    public GuiBusinessLogic(
            IRoomManagementService roomManagementService,
            // TODO: String literal
            @Qualifier("triggerServiceBaseUrlGetter") IServiceBaseUrlGetter triggerServiceBaseUrlGetter,
            @Qualifier("roomStatusMeetingExtension") Duration roomStatusMeetingExtension) {
        this.roomManagementService = roomManagementService;
        this.triggerServiceBaseUrlGetter = triggerServiceBaseUrlGetter;
        this.roomStatusMeetingExtension = roomStatusMeetingExtension;
    }

    @Override
    public String getCurrentMeetingStatusPage(RoomId roomId, Model model) {
        URL triggerServiceBaseUrl = this.triggerServiceBaseUrlGetter.getBaseUrl();
        // TODO: String literals
        model.addAttribute("roomId", roomId.getIdValue());
        IMeeting currentMeeting;
        try {
            currentMeeting = this.roomManagementService.getCurrentMeeting(roomId);
        }
        catch(EntityNotFoundException e) {
            return "meeting-status-not-available";
        }
        model.addAttribute("meetingTopic", currentMeeting.getTitle());
        model.addAttribute("minutesLeft", Duration.between(Instant.now(), currentMeeting.getEnd()).toMinutes());
        model.addAttribute("secondsLeft", Duration.between(Instant.now(), currentMeeting.getEnd()).toMillis() / 1000);
        model.addAttribute("startMeetingUrl", "http://localhost:8081" + format(TriggerApiConstants.URL_TEMPLATE_START_CURRENT_MEETING_BY_ROOM_ID, roomId));
        model.addAttribute("stopMeetingUrl", "http://localhost:8081" + format(TriggerApiConstants.URL_TEMPLATE_STOP_CURRENT_MEETING_BY_ROOM_ID, roomId));
        model.addAttribute("extendMeetingUrl", "http://localhost:8086" + format(RoomManagementApiConstants.URL_TEMPLATE_EXTEND_CURRENT_MEETING, roomId, Constants.DEFAULT_MEETING_EXTENSION.toMinutes()));
        model.addAttribute("startMeetingUrl", triggerServiceBaseUrl + format(TriggerApiConstants.URL_TEMPLATE_START_CURRENT_MEETING_BY_ROOM_ID, roomId.getIdValue()));
        model.addAttribute("stopMeetingUrl", triggerServiceBaseUrl + format(TriggerApiConstants.URL_TEMPLATE_STOP_CURRENT_MEETING_BY_ROOM_ID, roomId.getIdValue()));
        model.addAttribute("extendMeetingUrl", triggerServiceBaseUrl + format(RoomManagementApiConstants.URL_TEMPLATE_EXTEND_CURRENT_MEETING, roomId.getIdValue(), this.roomStatusMeetingExtension.toMinutes()));
        // TODO: Incorporate meeting details in page.
        return "meeting-status";
    }

    @Override
    public String getCurrentMeetingAgendaPage(RoomId roomId, Model model) {
        // TODO: String literals
        IMeeting currentMeeting;
        try {
            currentMeeting = this.roomManagementService.getCurrentMeeting(roomId);
        }
        catch(EntityNotFoundException e) {
            model.addAttribute("roomId", roomId.getIdValue());
            return "meeting-agenda-not-available";
        }
        model.addAttribute("meetingId", currentMeeting.getId().getIdValue());
        model.addAttribute("meetingTopic", currentMeeting.getTitle());
        model.addAttribute("agendaItems", currentMeeting.getAgenda());
        return "meeting-agenda";
    }
}
