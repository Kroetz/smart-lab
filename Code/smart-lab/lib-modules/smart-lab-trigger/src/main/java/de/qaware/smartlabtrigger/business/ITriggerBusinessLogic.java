package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import org.springframework.lang.Nullable;

import java.net.URL;

public interface ITriggerBusinessLogic {

    IJobInfo setUpCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl);
    IJobInfo setUpMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    IJobInfo setUpCurrentMeeting(IRoom room, @Nullable URL callbackUrl);
    IJobInfo setUpCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl);
    IJobInfo setUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);

    IJobInfo cleanUpCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl);
    IJobInfo cleanUpMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    IJobInfo cleanUpCurrentMeeting(IRoom room, @Nullable URL callbackUrl);
    IJobInfo cleanUpCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl);
    IJobInfo cleanUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);

    IJobInfo startCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl);
    IJobInfo startMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    IJobInfo startCurrentMeeting(IRoom room, @Nullable URL callbackUrl);
    IJobInfo startCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl);
    IJobInfo startCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);

    IJobInfo stopCurrentMeetingByRoomId(String roomId, @Nullable URL callbackUrl);
    IJobInfo stopMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    IJobInfo stopCurrentMeeting(IRoom room, @Nullable URL callbackUrl);
    IJobInfo stopCurrentMeetingByWorkgroupId(String workgroupId, @Nullable URL callbackUrl);
    IJobInfo stopCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);
}
