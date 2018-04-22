package de.qaware.smartlabcore.trigger.service;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcore.generic.result.CleanUpMeetingResult;
import de.qaware.smartlabcore.generic.result.SetUpMeetingResult;
import de.qaware.smartlabcore.generic.result.StartMeetingResult;
import de.qaware.smartlabcore.generic.result.StopMeetingResult;

public interface ITriggerService {

    SetUpMeetingResult setUpCurrentMeetingByRoomId(String roomId);
    SetUpMeetingResult setUpMeeting(IMeeting meeting);
    SetUpMeetingResult setUpCurrentMeeting(Room room);
    SetUpMeetingResult setUpCurrentMeetingByWorkgroupId(String workgroupId);
    SetUpMeetingResult setUpCurrentMeeting(Workgroup workgroup);

    CleanUpMeetingResult cleanUpCurrentMeetingByRoomId(String roomId);
    CleanUpMeetingResult cleanUpMeeting(IMeeting meeting);
    CleanUpMeetingResult cleanUpCurrentMeeting(Room room);
    CleanUpMeetingResult cleanUpCurrentMeetingByWorkgroupId(String workgroupId);
    CleanUpMeetingResult cleanUpCurrentMeeting(Workgroup workgroup);

    StartMeetingResult startCurrentMeetingByRoomId(String roomId);
    StartMeetingResult startMeeting(IMeeting meeting);
    StartMeetingResult startCurrentMeeting(Room room);
    StartMeetingResult startCurrentMeetingByWorkgroupId(String workgroupId);
    StartMeetingResult startCurrentMeeting(Workgroup workgroup);

    StopMeetingResult stopCurrentMeetingByRoomId(String roomId);
    StopMeetingResult stopMeeting(IMeeting meeting);
    StopMeetingResult stopCurrentMeeting(Room room);
    StopMeetingResult stopCurrentMeetingByWorkgroupId(String workgroupId);
    StopMeetingResult stopCurrentMeeting(Workgroup workgroup);
}
