package de.qaware.smartlabcore.trigger.service;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;

public interface ITriggerService {

    void setUpCurrentMeetingByRoomId(long roomId);
    void setUpMeeting(IMeeting meeting);
    void setUpCurrentMeeting(Room room);
    void setUpCurrentMeetingByWorkgroupId(long workgroupId);
    void setUpCurrentMeeting(Workgroup workgroup);

    void cleanUpCurrentMeetingByRoomId(long roomId);
    void cleanUpMeeting(IMeeting meeting);
    void cleanUpCurrentMeeting(Room room);
    void cleanUpCurrentMeetingByWorkgroupId(long workgroupId);
    void cleanUpCurrentMeeting(Workgroup workgroup);

    void startCurrentMeetingByRoomId(long roomId);
    void startMeeting(IMeeting meeting);
    void startCurrentMeeting(Room room);
    void startCurrentMeetingByWorkgroupId(long workgroupId);
    void startCurrentMeeting(Workgroup workgroup);

    void stopCurrentMeetingByRoomId(long roomId);
    void stopMeeting(IMeeting meeting);
    void stopCurrentMeeting(Room room);
    void stopCurrentMeetingByWorkgroupId(long workgroupId);
    void stopCurrentMeeting(Workgroup workgroup);
}
