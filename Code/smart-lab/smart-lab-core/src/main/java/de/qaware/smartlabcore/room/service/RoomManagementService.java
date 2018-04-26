package de.qaware.smartlabcore.room.service;

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import de.qaware.smartlabcore.room.repository.IRoomManagementRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RoomManagementService implements IRoomManagementService {

    private final IRoomManagementRepository roomManagementRepository;
    private final IMeetingManagementApiClient meetingManagementApiClient;

    public RoomManagementService(
            IRoomManagementRepository roomManagementRepository,
            IMeetingManagementApiClient meetingManagementApiClient) {
        this.roomManagementRepository = roomManagementRepository;
        this.meetingManagementApiClient = meetingManagementApiClient;
    }

    public Set<IRoom> getRooms() {
        return roomManagementRepository.findAll();
    }

    @Override
    public Optional<IRoom> getRoom(String roomId) {
        return roomManagementRepository.findOne(roomId);
    }

    @Override
    public CreationResult createRoom(IRoom room) {
        return roomManagementRepository.create(room);
    }

    @Override
    public DeletionResult deleteRoom(String roomId) {
        return roomManagementRepository.delete(roomId);
    }

    @Override
    public Optional<Set<IMeeting>> getMeetingsInRoom(String roomId) {
        return roomManagementRepository.getMeetingsInRoom(roomId);
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(String roomId) {
        return roomManagementRepository.getCurrentMeeting(roomId);
    }

    @Override
    public ExtensionResult extendCurrentMeeting(String roomId, Duration extension) {
        return roomManagementRepository.extendCurrentMeeting(roomId, extension);
    }

    @Override
    public String getCurrentMeetingStatusPage(String roomId, Model model) {
        val statusPage = getCurrentMeeting(roomId)
                .map(meeting -> {
                    model.addAttribute("roomId", roomId);
                    model.addAttribute("meetingTopic", meeting.getTitle());
                    model.addAttribute("minutesLeft", Duration.between(Instant.now(), meeting.getEnd()).toMinutes());
                    model.addAttribute("secondsLeft", Duration.between(Instant.now(), meeting.getEnd()).toMillis() / 1000);
                    model.addAttribute("startMeetingUrl", "http://localhost:8080/smart-lab/api/trigger/room/" + roomId + "/start-current-meeting");
                    model.addAttribute("stopMeetingUrl", "http://localhost:8080/smart-lab/api/trigger/room/" + roomId + "/stop-current-meeting");
                    model.addAttribute("extendMeetingUrl", "http://localhost:8080/smart-lab/api/room/" + roomId + "/extend-current-meeting?extension-in-minutes=1");
                    // TODO: Incorporate meeting details in page.
                    return "meeting-status";
                })
                .orElse("meeting-status-not-available");
        return statusPage;
    }
}
