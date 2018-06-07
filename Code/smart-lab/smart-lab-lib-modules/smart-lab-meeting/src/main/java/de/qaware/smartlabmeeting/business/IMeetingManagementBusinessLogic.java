package de.qaware.smartlabmeeting.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.result.ShiftResult;
import de.qaware.smartlabcore.result.ShorteningResult;
import de.qaware.smartlabcore.generic.business.IEntityManagementBusinessLogic;

import java.time.Duration;

public interface IMeetingManagementBusinessLogic extends IEntityManagementBusinessLogic<IMeeting> {

    ShorteningResult shortenMeeting(String meetingId, Duration shortening);
    ExtensionResult extendMeeting(String meetingId, Duration extension);
    ShiftResult shiftMeeting(String meetingId, Duration shift);
}
