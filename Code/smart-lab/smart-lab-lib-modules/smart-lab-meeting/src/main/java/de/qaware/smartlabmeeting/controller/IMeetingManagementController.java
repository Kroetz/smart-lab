package de.qaware.smartlabmeeting.controller;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.RoomId;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

public interface IMeetingManagementController {

    Map<RoomId, Set<IMeeting>> findAll();
    Set<IMeeting> findAll(String roomId);
    ResponseEntity<IMeeting> findOne(String meetingId, String roomId);
    ResponseEntity<Set<IMeeting>> findMultiple(String[] meetingIds, String roomId);
    ResponseEntity<Void> create(IMeeting meeting);
    ResponseEntity<Void> delete(String meetingId, String roomId);
    ResponseEntity<Void> shortenMeeting(
            String meetingId,
            String roomId,
            long shorteningInMinutes);
    ResponseEntity<Void> extendMeeting(
            String meetingId,
            String roomId,
            long extensionInMinutes);
    ResponseEntity<Void> shiftMeeting(
            String meetingId,
            String roomId,
            long shiftInMinutes);
}
