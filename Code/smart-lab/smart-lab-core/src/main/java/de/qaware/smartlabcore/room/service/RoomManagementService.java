package de.qaware.smartlabcore.room.service;

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.room.repository.IRoomManagementRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

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

    public List<IRoom> getRooms() {
        return roomManagementRepository.getRooms();
    }

    @Override
    public Optional<IRoom> getRoom(long roomId) {
        return roomManagementRepository.getRoom(roomId);
    }

    @Override
    public boolean createRoom(IRoom room) {
        return roomManagementRepository.createRoom(room);
    }

    @Override
    public void deleteRoom(long roomId) {
        roomManagementRepository.deleteRoom(roomId);
    }

    @Override
    public List<IMeeting> getMeetingsInRoom(long roomId) {
        return roomManagementRepository.getMeetingsInRoom(roomId);
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(long roomId) {
        return roomManagementRepository.getCurrentMeeting(roomId);
    }

    @Override
    public boolean extendCurrentMeeting(long roomId, Duration extension) {
        return roomManagementRepository.extendCurrentMeeting(roomId, extension);
    }

    @Override
    public String getCurrentMeetingStatusPage(long roomId, Model model) {
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
