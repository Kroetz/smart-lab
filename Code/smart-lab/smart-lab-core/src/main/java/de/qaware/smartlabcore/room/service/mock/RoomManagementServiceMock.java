package de.qaware.smartlabcore.room.service.mock;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;
import de.qaware.smartlabcore.meeting.service.IMeetingManagementService;
import de.qaware.smartlabcore.room.service.IRoomManagementService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mock")
@Slf4j
public class RoomManagementServiceMock implements IRoomManagementService {

    @Autowired
    @Qualifier("mock")
    private IMeetingManagementService meetingService;

    private final IRoomConfigProviderMock roomConfigProvider;

    @Autowired
    public RoomManagementServiceMock(
            @Qualifier("mock") IRoomConfigProviderMock roomConfigProvider) {
        this.roomConfigProvider = roomConfigProvider;
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
    public Optional<IMeeting> getCurrentMeeting(long roomId) {
        return getRoom(roomId)
                .map(meetingService::getCurrentMeeting)
                .orElse(Optional.empty());
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
    public void extendCurrentMeeting(long roomId, long extensionInMinutes) {
        getRoom(roomId).ifPresent(room -> meetingService.extendCurrentMeeting(room, Duration.ofMinutes(extensionInMinutes)));
    }

    @Override
    public String getCurrentMeetingStatusPage(long roomId, Model model) {
        val currentMeeting = getRoom(roomId)
                .map(room -> meetingService.getCurrentMeeting(room))
                .orElse(Optional.empty());
        val statusPage = currentMeeting
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
