package de.qaware.smartlabapi.service.meeting;

import de.qaware.smartlabapi.service.generic.IEntityManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;

import java.time.Duration;

public interface IMeetingManagementService extends IEntityManagementService<IMeeting> {

    void shortenMeeting(String meetingId, Duration shortening);
    void extendMeeting(String meetingId, Duration extension);
    void shiftMeeting(String meetingId, Duration shift);
}
