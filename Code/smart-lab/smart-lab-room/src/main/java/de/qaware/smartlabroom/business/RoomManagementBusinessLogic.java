package de.qaware.smartlabroom.business;

import de.qaware.smartlabcommons.api.RoomManagementApiConstants;
import de.qaware.smartlabcommons.api.TriggerApiConstants;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.result.ExtensionResult;
import de.qaware.smartlabcore.generic.business.AbstractEntityManagementBusinessLogic;
import de.qaware.smartlabroom.repository.IRoomManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RoomManagementBusinessLogic extends AbstractEntityManagementBusinessLogic<IRoom> implements IRoomManagementBusinessLogic {

    private static final String APP_PROPERTY_DEFAULT_MEETING_EXTENSION_IN_MINUTES = "default.meeting.extension.in.minutes";
    public static Duration DEFAULT_MEETING_EXTENSION;
    private final IRoomManagementRepository roomManagementRepository;

    public RoomManagementBusinessLogic(
            @Value("${" + APP_PROPERTY_DEFAULT_MEETING_EXTENSION_IN_MINUTES + "}") long defaultMeetingExtensionInMinutes,
            IRoomManagementRepository roomManagementRepository) {
        super(roomManagementRepository);
        DEFAULT_MEETING_EXTENSION = Duration.ofMinutes(defaultMeetingExtensionInMinutes);
        this.roomManagementRepository = roomManagementRepository;
    }

    @Override
    public Optional<Set<IMeeting>> getMeetingsInRoom(String roomId) {
        return roomManagementRepository.findOne(roomId)
                .map(room -> Optional.of(roomManagementRepository.getMeetingsInRoom(room)))
                .orElse(Optional.empty());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(String roomId) {
        return roomManagementRepository.findOne(roomId)
                .map(roomManagementRepository::getCurrentMeeting)
                .orElse(Optional.empty());
    }

    @Override
    public ExtensionResult extendCurrentMeeting(String roomId, Duration extension) {
        return roomManagementRepository.findOne(roomId)
                .map(room -> roomManagementRepository.extendCurrentMeeting(room, extension))
                .orElse(ExtensionResult.NOT_FOUND);
    }

    @Override
    public String getCurrentMeetingStatusPage(String roomId, Model model) {
        String statusPage = getCurrentMeeting(roomId)
                .map(meeting -> {
                    model.addAttribute("roomId", roomId);
                    model.addAttribute("meetingTopic", meeting.getTitle());
                    model.addAttribute("minutesLeft", Duration.between(Instant.now(), meeting.getEnd()).toMinutes());
                    model.addAttribute("secondsLeft", Duration.between(Instant.now(), meeting.getEnd()).toMillis() / 1000);
                    model.addAttribute("startMeetingUrl", "http://localhost:8081" + String.format(TriggerApiConstants.URL_TEMPLATE_START_CURRENT_MEETING_BY_ROOM_ID, roomId));
                    model.addAttribute("stopMeetingUrl", "http://localhost:8081" + String.format(TriggerApiConstants.URL_TEMPLATE_STOP_CURRENT_MEETING_BY_ROOM_ID, roomId));
                    model.addAttribute("extendMeetingUrl", "http://localhost:8086" + String.format(RoomManagementApiConstants.URL_TEMPLATE_EXTEND_CURRENT_MEETING, roomId, DEFAULT_MEETING_EXTENSION.toMinutes()));
                    /*model.addAttribute("startMeetingUrl", TriggerApiConstants.FEIGN_CLIENT_URL + String.format(TriggerApiConstants.URL_TEMPLATE_START_CURRENT_MEETING_BY_ROOM_ID, roomId));
                    model.addAttribute("stopMeetingUrl", TriggerApiConstants.FEIGN_CLIENT_URL + String.format(TriggerApiConstants.URL_TEMPLATE_STOP_CURRENT_MEETING_BY_ROOM_ID, roomId));
                    model.addAttribute("extendMeetingUrl", RoomManagementApiConstants.FEIGN_CLIENT_URL + String.format(RoomManagementApiConstants.URL_TEMPLATE_EXTEND_CURRENT_MEETING, roomId, DEFAULT_MEETING_EXTENSION.toMinutes()));*/
                    // TODO: Incorporate meeting details in page.
                    return "meeting-status";
                })
                .orElse("meeting-status-not-available");
        return statusPage;
    }
}
