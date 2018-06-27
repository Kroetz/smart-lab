package de.qaware.smartlabroom.business;

import de.qaware.smartlabapi.RoomManagementApiConstants;
import de.qaware.smartlabapi.TriggerApiConstants;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.generic.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlabcore.miscellaneous.Constants;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabroom.repository.IRoomManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.*;

@Service
@Slf4j
public class RoomManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IRoom, RoomId> implements IRoomManagementBusinessLogic {

    private final IRoomManagementRepository roomManagementRepository;

    public RoomManagementBusinessLogic(IRoomManagementRepository roomManagementRepository) {
        super(roomManagementRepository);
        this.roomManagementRepository = roomManagementRepository;
    }

    @Override
    public Optional<Set<IMeeting>> getMeetingsInRoom(RoomId roomId) {
        return this.roomManagementRepository.findOne(roomId)
                .map(room -> Optional.of(this.roomManagementRepository.getMeetingsInRoom(room)))
                .orElse(Optional.empty());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(RoomId roomId) {
        return this.roomManagementRepository.findOne(roomId)
                .map(this.roomManagementRepository::getCurrentMeeting)
                .orElse(Optional.empty());
    }

    @Override
    public ExtensionResult extendCurrentMeeting(RoomId roomId, Duration extension) {
        return this.roomManagementRepository.findOne(roomId)
                .map(room -> this.roomManagementRepository.extendCurrentMeeting(room, extension))
                .orElse(ExtensionResult.NOT_FOUND);
    }

    @Override
    public String getCurrentMeetingStatusPage(RoomId roomId, Model model) {
        String statusPage = getCurrentMeeting(roomId)
                .map(meeting -> {
                    model.addAttribute("roomId", roomId);
                    model.addAttribute("meetingTopic", meeting.getTitle());
                    model.addAttribute("minutesLeft", Duration.between(Instant.now(), meeting.getEnd()).toMinutes());
                    model.addAttribute("secondsLeft", Duration.between(Instant.now(), meeting.getEnd()).toMillis() / 1000);
                    model.addAttribute("startMeetingUrl", "http://localhost:8081" + format(TriggerApiConstants.URL_TEMPLATE_START_CURRENT_MEETING_BY_ROOM_ID, roomId));
                    model.addAttribute("stopMeetingUrl", "http://localhost:8081" + format(TriggerApiConstants.URL_TEMPLATE_STOP_CURRENT_MEETING_BY_ROOM_ID, roomId));
                    model.addAttribute("extendMeetingUrl", "http://localhost:8086" + format(RoomManagementApiConstants.URL_TEMPLATE_EXTEND_CURRENT_MEETING, roomId, Constants.DEFAULT_MEETING_EXTENSION.toMinutes()));
                    /*model.addAttribute("startMeetingUrl", TriggerApiConstants.FEIGN_CLIENT_URL + format(TriggerApiConstants.URL_TEMPLATE_START_CURRENT_MEETING_BY_ROOM_ID, roomId));
                    model.addAttribute("stopMeetingUrl", TriggerApiConstants.FEIGN_CLIENT_URL + format(TriggerApiConstants.URL_TEMPLATE_STOP_CURRENT_MEETING_BY_ROOM_ID, roomId));
                    model.addAttribute("extendMeetingUrl", RoomManagementApiConstants.FEIGN_CLIENT_URL + format(RoomManagementApiConstants.URL_TEMPLATE_EXTEND_CURRENT_MEETING, roomId, DEFAULT_MEETING_EXTENSION.toMinutes()));*/
                    // TODO: Incorporate meeting details in page.
                    return "meeting-status";
                })
                .orElse("meeting-status-not-available");
        return statusPage;
    }
}
