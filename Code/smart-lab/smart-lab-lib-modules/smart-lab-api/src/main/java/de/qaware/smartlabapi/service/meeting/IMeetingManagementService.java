package de.qaware.smartlabapi.service.meeting;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

public interface IMeetingManagementService {

    Map<RoomId, Set<IMeeting>> findAll();
    Set<IMeeting> findAll(RoomId roomId);
    IMeeting findOne(MeetingId entityId, RoomId roomId);
    Set<IMeeting> findMultiple(MeetingId[] entityIds, RoomId roomId);
    void create(IMeeting meeting);
    void delete(MeetingId entityId, RoomId roomId);
    void shortenMeeting(MeetingId meetingId, RoomId roomId, Duration shortening);
    void extendMeeting(MeetingId meetingId, RoomId roomId, Duration extension);
    void shiftMeeting(MeetingId meetingId, RoomId roomId, Duration shift);
}
