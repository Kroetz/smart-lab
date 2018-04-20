package de.qaware.smartlabcore.trigger.service;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;

public interface ITriggerService {

    void setUpCurrentMeetingByRoomId(String roomId);
    void setUpMeeting(IMeeting meeting);
    void setUpCurrentMeeting(Room room);
    void setUpCurrentMeetingByWorkgroupId(String workgroupId);
    void setUpCurrentMeeting(Workgroup workgroup);

    void cleanUpCurrentMeetingByRoomId(String roomId);
    void cleanUpMeeting(IMeeting meeting);
    void cleanUpCurrentMeeting(Room room);
    void cleanUpCurrentMeetingByWorkgroupId(String workgroupId);
    void cleanUpCurrentMeeting(Workgroup workgroup);

    void startCurrentMeetingByRoomId(String roomId);
    void startMeeting(IMeeting meeting);
    void startCurrentMeeting(Room room);
    void startCurrentMeetingByWorkgroupId(String workgroupId);
    void startCurrentMeeting(Workgroup workgroup);

    void stopCurrentMeetingByRoomId(String roomId);
    void stopMeeting(IMeeting meeting);
    void stopCurrentMeeting(Room room);
    void stopCurrentMeetingByWorkgroupId(String workgroupId);
    void stopCurrentMeeting(Workgroup workgroup);
}
