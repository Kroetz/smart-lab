package de.qaware.smartlabroom.business;

import de.qaware.smartlabapi.RoomManagementApiConstants;
import de.qaware.smartlabapi.TriggerApiConstants;
import de.qaware.smartlabapi.service.IServiceBaseUrlGetter;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.generic.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlabcore.miscellaneous.Constants;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabroom.repository.IRoomManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

@Service
@Slf4j
public class RoomManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IRoom, RoomId> implements IRoomManagementBusinessLogic {

    private final IRoomManagementRepository roomManagementRepository;
    private final IServiceBaseUrlGetter triggerServiceBaseUrlGetter;
    // TODO: String literal
    private final Duration roomStatusMeetingExtension;

    public RoomManagementBusinessLogic(
            IRoomManagementRepository roomManagementRepository,
            // TODO: String literal
            @Qualifier("triggerServiceBaseUrlGetter") IServiceBaseUrlGetter triggerServiceBaseUrlGetter,
            @Qualifier("roomStatusMeetingExtension") Duration roomStatusMeetingExtension) {
        super(roomManagementRepository);
        this.roomManagementRepository = roomManagementRepository;
        this.triggerServiceBaseUrlGetter = triggerServiceBaseUrlGetter;
        this.roomStatusMeetingExtension = roomStatusMeetingExtension;
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
        URL triggerBaseUrl = this.triggerServiceBaseUrlGetter.getBaseUrl();
        String statusPage = getCurrentMeeting(roomId)
                .map(meeting -> {
                    // TODO: String literals
                    model.addAttribute("roomId", roomId.getIdValue());
                    model.addAttribute("meetingTopic", meeting.getTitle());
                    model.addAttribute("minutesLeft", Duration.between(Instant.now(), meeting.getEnd()).toMinutes());
                    model.addAttribute("secondsLeft", Duration.between(Instant.now(), meeting.getEnd()).toMillis() / 1000);
                    model.addAttribute("startMeetingUrl", "http://localhost:8081" + format(TriggerApiConstants.URL_TEMPLATE_START_CURRENT_MEETING_BY_ROOM_ID, roomId));
                    model.addAttribute("stopMeetingUrl", "http://localhost:8081" + format(TriggerApiConstants.URL_TEMPLATE_STOP_CURRENT_MEETING_BY_ROOM_ID, roomId));
                    model.addAttribute("extendMeetingUrl", "http://localhost:8086" + format(RoomManagementApiConstants.URL_TEMPLATE_EXTEND_CURRENT_MEETING, roomId, Constants.DEFAULT_MEETING_EXTENSION.toMinutes()));
                    model.addAttribute("startMeetingUrl", triggerBaseUrl + format(TriggerApiConstants.URL_TEMPLATE_START_CURRENT_MEETING_BY_ROOM_ID, roomId.getIdValue()));
                    model.addAttribute("stopMeetingUrl", triggerBaseUrl + format(TriggerApiConstants.URL_TEMPLATE_STOP_CURRENT_MEETING_BY_ROOM_ID, roomId.getIdValue()));
                    model.addAttribute("extendMeetingUrl", triggerBaseUrl + format(RoomManagementApiConstants.URL_TEMPLATE_EXTEND_CURRENT_MEETING, roomId.getIdValue(), this.roomStatusMeetingExtension.toMinutes()));
                    // TODO: Incorporate meeting details in page.
                    return "meeting-status";
                })
                .orElse("meeting-status-not-available");
        return statusPage;
    }
}
