package de.qaware.smartlabmeeting.service.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.result.ShiftResult;
import de.qaware.smartlabcore.result.ShorteningResult;
import de.qaware.smartlabcore.service.business.IBasicEntityManagementBusinessLogic;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IMeetingManagementBusinessLogic extends IBasicEntityManagementBusinessLogic<IMeeting, MeetingId> {

    Set<IMeeting> findAll(RoomId roomId);
    Set<IMeeting> findAll(WorkgroupId workgroupId);
    Set<IMeeting> findAllCurrent();
    Optional<IMeeting> findCurrent(RoomId roomId);
    Optional<IMeeting> findCurrent(WorkgroupId workgroupId);
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
