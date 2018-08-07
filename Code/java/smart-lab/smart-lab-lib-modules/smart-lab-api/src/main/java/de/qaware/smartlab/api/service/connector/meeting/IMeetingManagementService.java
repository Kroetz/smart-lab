package de.qaware.smartlab.api.service.connector.meeting;

import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingDto;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;

import java.time.Duration;
import java.util.Set;

public interface IMeetingManagementService extends IBasicEntityManagementService<IMeeting, MeetingId, MeetingDto> {

    Set<IMeeting> findAll();
    Set<IMeeting> findAll(LocationId locationId);
    Set<IMeeting> findAll(WorkgroupId workgroupId);
    Set<IMeeting> findAllCurrent();
    IMeeting findOne(MeetingId meetingId);
    Set<IMeeting> findMultiple(MeetingId[] meetingIds);
    IMeeting findCurrent(LocationId locationId);
    IMeeting findCurrent(WorkgroupId workgroupId);
    IMeeting create(IMeeting meeting);
    void delete(MeetingId meetingId);
    void shortenMeeting(MeetingId meetingId, Duration shortening);
    void extendMeeting(MeetingId meetingId, Duration extension);
    void shiftMeeting(MeetingId meetingId, Duration shift);
}
