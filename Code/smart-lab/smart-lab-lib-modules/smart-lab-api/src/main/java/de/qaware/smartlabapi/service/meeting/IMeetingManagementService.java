package de.qaware.smartlabapi.service.meeting;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;

import java.time.Duration;
import java.util.Set;

public interface IMeetingManagementService {

    Set<IMeeting> findAll();
    Set<IMeeting> findAll(RoomId roomId);
    IMeeting findOne(MeetingId entityId);
    Set<IMeeting> findMultiple(MeetingId[] entityIds);
    void create(IMeeting meeting);
    void delete(MeetingId entityId);
    void shortenMeeting(MeetingId meetingId, Duration shortening);
    void extendMeeting(MeetingId meetingId, Duration extension);
    void shiftMeeting(MeetingId meetingId, Duration shift);
}
