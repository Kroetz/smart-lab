package de.qaware.smartlabmeeting.service.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.generic.business.IBasicEntityManagementBusinessLogic;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.result.ShiftResult;
import de.qaware.smartlabcore.result.ShorteningResult;

import java.time.Duration;
import java.util.Set;

public interface IMeetingManagementBusinessLogic extends IBasicEntityManagementBusinessLogic<IMeeting, MeetingId> {

    Set<IMeeting> findAll(RoomId roomId);
    Set<IMeeting> findAllCurrent();
    ShorteningResult shortenMeeting(
            MeetingId meetingId,
            Duration shortening);
    ExtensionResult extendMeeting(
            MeetingId meetingId,
            Duration extension);
    ShiftResult shiftMeeting(
            MeetingId meetingId,
            Duration shift);
}