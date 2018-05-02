package de.qaware.smartlabcommons.api.service.room;

import de.qaware.smartlabcommons.api.client.IRoomManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RoomManagementService implements IRoomManagementService {

    private IRoomManagementApiClient roomManagementApiClient;

    public RoomManagementService(IRoomManagementApiClient roomManagementApiClient) {
        this.roomManagementApiClient = roomManagementApiClient;
    }

    @Override
    public Set<IRoom> findAll() {
        return roomManagementApiClient.findAll();
    }

    @Override
    public ResponseEntity<IRoom> findOne(String roomId) {
        return roomManagementApiClient.findOne(roomId);
    }

    @Override
    public ResponseEntity<Set<IRoom>> findMultiple(String[] roomIds) {
        return roomManagementApiClient.findMultiple(roomIds);
    }

    @Override
    public ResponseEntity<Void> create(IRoom room) {
        return roomManagementApiClient.create(room);
    }

    @Override
    public ResponseEntity<Void> delete(String roomId) {
        return roomManagementApiClient.delete(roomId);
    }

    @Override
    public ResponseEntity<Set<IMeeting>> getMeetingsInRoom(String roomId) {
        return roomManagementApiClient.getMeetingsInRoom(roomId);
    }

    @Override
    public ResponseEntity<IMeeting> getCurrentMeeting(String roomId) {
        return roomManagementApiClient.getCurrentMeeting(roomId);
    }

    @Override
    public ResponseEntity<Void> extendCurrentMeeting(String roomId, long extensionInMinutes) {
        return roomManagementApiClient.extendCurrentMeeting(roomId, extensionInMinutes);
    }
}
