package de.qaware.smartlab.event.management.service.business;

import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.meeting.MeetingId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.result.ExtensionResult;
import de.qaware.smartlab.core.result.ShiftResult;
import de.qaware.smartlab.core.result.ShorteningResult;
import de.qaware.smartlab.core.service.business.IBasicEntityManagementBusinessLogic;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IMeetingManagementBusinessLogic extends IBasicEntityManagementBusinessLogic<IMeeting, MeetingId> {

    Set<IMeeting> findAll(LocationId locationId);
    Set<IMeeting> findAll(WorkgroupId workgroupId);
    Set<IMeeting> findAllCurrent();
    Optional<IMeeting> findCurrent(LocationId locationId);
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
