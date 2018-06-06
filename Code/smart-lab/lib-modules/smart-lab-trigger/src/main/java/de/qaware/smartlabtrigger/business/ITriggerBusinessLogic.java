package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.result.CleanUpMeetingResult;
import de.qaware.smartlabcore.result.SetUpMeetingResult;
import de.qaware.smartlabcore.result.StartMeetingResult;
import de.qaware.smartlabcore.result.StopMeetingResult;
import org.springframework.lang.Nullable;

import java.net.URL;

public interface ITriggerBusinessLogic {

    SetUpMeetingResult setUpCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl);
    SetUpMeetingResult setUpMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    SetUpMeetingResult setUpCurrentMeeting(IRoom room, @Nullable URL callbackUrl);
    SetUpMeetingResult setUpCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl);
    SetUpMeetingResult setUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);

    CleanUpMeetingResult cleanUpCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl);
    CleanUpMeetingResult cleanUpMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    CleanUpMeetingResult cleanUpCurrentMeeting(IRoom room, @Nullable URL callbackUrl);
    CleanUpMeetingResult cleanUpCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl);
    CleanUpMeetingResult cleanUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);

    StartMeetingResult startCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl);
    StartMeetingResult startMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    StartMeetingResult startCurrentMeeting(IRoom room, @Nullable URL callbackUrl);
    StartMeetingResult startCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl);
    StartMeetingResult startCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);

    StopMeetingResult stopCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl);
    StopMeetingResult stopMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    StopMeetingResult stopCurrentMeeting(IRoom room, @Nullable URL callbackUrl);
    StopMeetingResult stopCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl);
    StopMeetingResult stopCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);
}
