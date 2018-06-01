package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.result.CleanUpMeetingResult;
import de.qaware.smartlabcore.result.SetUpMeetingResult;
import de.qaware.smartlabcore.result.StartMeetingResult;
import de.qaware.smartlabcore.result.StopMeetingResult;

public interface ITriggerBusinessLogic {

    SetUpMeetingResult setUpCurrentMeetingByRoomId(String roomId);
    SetUpMeetingResult setUpMeeting(IMeeting meeting);
    SetUpMeetingResult setUpCurrentMeeting(IRoom room);
    SetUpMeetingResult setUpCurrentMeetingByWorkgroupId(String workgroupId);
    SetUpMeetingResult setUpCurrentMeeting(IWorkgroup workgroup);

    CleanUpMeetingResult cleanUpCurrentMeetingByRoomId(String roomId);
    CleanUpMeetingResult cleanUpMeeting(IMeeting meeting);
    CleanUpMeetingResult cleanUpCurrentMeeting(IRoom room);
    CleanUpMeetingResult cleanUpCurrentMeetingByWorkgroupId(String workgroupId);
    CleanUpMeetingResult cleanUpCurrentMeeting(IWorkgroup workgroup);

    StartMeetingResult startCurrentMeetingByRoomId(String roomId);
    StartMeetingResult startMeeting(IMeeting meeting);
    StartMeetingResult startCurrentMeeting(IRoom room);
    StartMeetingResult startCurrentMeetingByWorkgroupId(String workgroupId);
    StartMeetingResult startCurrentMeeting(IWorkgroup workgroup);

    StopMeetingResult stopCurrentMeetingByRoomId(String roomId);
    StopMeetingResult stopMeeting(IMeeting meeting);
    StopMeetingResult stopCurrentMeeting(IRoom room);
    StopMeetingResult stopCurrentMeetingByWorkgroupId(String workgroupId);
    StopMeetingResult stopCurrentMeeting(IWorkgroup workgroup);
}
