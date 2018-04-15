package de.qaware.smartlabcore.room.service.mock;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcore.room.service.IRoomManagementService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("mock")
@Slf4j
public class RoomManagementServiceMock implements IRoomManagementService {

    private final IRoomConfigProviderMockClient roomConfigProvider;
    private final IMeetingManagementApiClient meetingManagementApiClient;

    public RoomManagementServiceMock(
            @Qualifier("mock") IRoomConfigProviderMockClient roomConfigProvider,
            IMeetingManagementApiClient meetingManagementApiClient) {
        this.roomConfigProvider = roomConfigProvider;
        this.meetingManagementApiClient = meetingManagementApiClient;
    }

    public List<Room> getRooms() {
        return roomConfigProvider.getRooms();
    }

    @Override
    public Optional<Room> getRoom(long roomId) {
        Optional<Room> r = roomConfigProvider.getRoom(roomId);
        return r;

        //return roomConfigProvider.getRoom(roomId);
    }

    @Override
    public List<IMeeting> getMeetingsInRoom(long roomId) {
        return meetingManagementApiClient.getMeetings().stream()
                .filter(meeting -> meeting.getRoomId() == roomId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(long roomId) {
        return getMeetingsInRoom(roomId).stream()
                .filter(meeting -> meeting.getStart().isBefore(Instant.now()) && meeting.getEnd().isAfter(Instant.now()))
                .findFirst();
    }

    @Override
    public boolean createRoom(Room room) {
        return roomConfigProvider.createRoom(room);
    }

    @Override
    public void deleteRoom(long roomId) {
        roomConfigProvider.deleteRoom(roomId);
    }

    @Override
    public boolean extendCurrentMeeting(long roomId, long extensionInMinutes) {
        return getCurrentMeeting(roomId)
                .map(meeting -> meetingManagementApiClient.extendMeeting(meeting.getId(), extensionInMinutes))
                .orElse(false);
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
