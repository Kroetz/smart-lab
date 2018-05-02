package de.qaware.smartlabcommons.api.service.room;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IRoomManagementService {

    Set<IRoom> findAll();
    ResponseEntity<IRoom> findOne(String roomId);
    ResponseEntity<Set<IRoom>> findMultiple(String[] roomIds);
    ResponseEntity<Void> create(IRoom room);
    ResponseEntity<Void> delete(String roomId);
    ResponseEntity<Set<IMeeting>> getMeetingsInRoom(String roomId);
    ResponseEntity<IMeeting> getCurrentMeeting(String roomId);
    ResponseEntity<Void> extendCurrentMeeting(String roomId, long extensionInMinutes);
    //String getCurrentMeetingStatusPage(String roomId);
}
