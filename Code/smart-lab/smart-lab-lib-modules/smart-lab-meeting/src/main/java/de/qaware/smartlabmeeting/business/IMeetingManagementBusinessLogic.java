package de.qaware.smartlabmeeting.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.result.ShiftResult;
import de.qaware.smartlabcore.result.ShorteningResult;
import de.qaware.smartlabcore.generic.business.IEntityManagementBusinessLogic;

import java.time.Duration;

public interface IMeetingManagementBusinessLogic extends IEntityManagementBusinessLogic<IMeeting, MeetingId> {

    ShorteningResult shortenMeeting(MeetingId meetingId, Duration shortening);
    ExtensionResult extendMeeting(MeetingId meetingId, Duration extension);
    ShiftResult shiftMeeting(MeetingId meetingId, Duration shift);
}
