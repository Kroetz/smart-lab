package de.qaware.smartlabapi.service.meeting;

import de.qaware.smartlabapi.service.generic.IEntityManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;

import java.time.Duration;

public interface IMeetingManagementService extends IEntityManagementService<IMeeting, MeetingId> {

    void shortenMeeting(MeetingId meetingId, Duration shortening);
    void extendMeeting(MeetingId meetingId, Duration extension);
    void shiftMeeting(MeetingId meetingId, Duration shift);
}
