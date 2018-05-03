package de.qaware.smartlabcommons.api.service.meeting;

import de.qaware.smartlabcommons.api.service.generic.IEntityManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;

import java.time.Duration;

public interface IMeetingManagementService extends IEntityManagementService<IMeeting> {

    void shortenMeeting(String meetingId, Duration shortening);
    void extendMeeting(String meetingId, Duration extension);
    void shiftMeeting(String meetingId, Duration shift);
}
