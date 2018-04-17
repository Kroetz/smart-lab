package de.qaware.smartlabcore.room.repository;

import de.qaware.smartlabcommons.api.configprovidermock.client.IRoomConfigProviderMockClient;
import de.qaware.smartlabcommons.api.management.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class RoomManagementRepositoryMock implements IRoomManagementRepository {

    private final IRoomConfigProviderMockClient roomConfigProviderMockClient;
    private final IMeetingManagementApiClient meetingManagementApiClient;

    public RoomManagementRepositoryMock(
            IRoomConfigProviderMockClient roomConfigProviderMockClient,
            IMeetingManagementApiClient meetingManagementApiClient) {
        this.roomConfigProviderMockClient = roomConfigProviderMockClient;
        this.meetingManagementApiClient = meetingManagementApiClient;
    }

    @Override
    public List<IRoom> getRooms() {
        return roomConfigProviderMockClient.getRooms();
    }

    @Override
    public Optional<IRoom> getRoom(long roomId) {
        return Optional.ofNullable(roomConfigProviderMockClient.getRoom(roomId).getBody());
    }

    @Override
    public boolean createRoom(IRoom room) {
        return roomConfigProviderMockClient.createRoom(room);
    }

    @Override
    public void deleteRoom(long roomId) {
        roomConfigProviderMockClient.deleteRoom(roomId);
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
    public boolean extendCurrentMeeting(long roomId, Duration extension) {
        return getCurrentMeeting(roomId)
                .map(meeting -> meetingManagementApiClient.extendMeeting(meeting.getId(), extension.toMinutes()))
                .orElse(false);
    }
}
