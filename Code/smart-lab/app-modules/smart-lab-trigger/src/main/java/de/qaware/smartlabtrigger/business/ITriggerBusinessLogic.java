package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.result.CleanUpMeetingResult;
import de.qaware.smartlabcommons.result.SetUpMeetingResult;
import de.qaware.smartlabcommons.result.StartMeetingResult;
import de.qaware.smartlabcommons.result.StopMeetingResult;

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
