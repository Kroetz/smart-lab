package de.qaware.smartlabgui.business;

import de.qaware.smartlabapi.RoomManagementApiConstants;
import de.qaware.smartlabapi.TriggerApiConstants;
import de.qaware.smartlabapi.service.IServiceBaseUrlGetter;
import de.qaware.smartlabapi.service.room.IRoomManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.miscellaneous.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;

import static java.lang.String.format;

@Service
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
        URL triggerBaseUrl = this.triggerServiceBaseUrlGetter.getBaseUrl();
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
        model.addAttribute("startMeetingUrl", triggerBaseUrl + format(TriggerApiConstants.URL_TEMPLATE_START_CURRENT_MEETING_BY_ROOM_ID, roomId.getIdValue()));
        model.addAttribute("stopMeetingUrl", triggerBaseUrl + format(TriggerApiConstants.URL_TEMPLATE_STOP_CURRENT_MEETING_BY_ROOM_ID, roomId.getIdValue()));
        model.addAttribute("extendMeetingUrl", triggerBaseUrl + format(RoomManagementApiConstants.URL_TEMPLATE_EXTEND_CURRENT_MEETING, roomId.getIdValue(), this.roomStatusMeetingExtension.toMinutes()));
        // TODO: Incorporate meeting details in page.
        return "meeting-status";
    }
}
