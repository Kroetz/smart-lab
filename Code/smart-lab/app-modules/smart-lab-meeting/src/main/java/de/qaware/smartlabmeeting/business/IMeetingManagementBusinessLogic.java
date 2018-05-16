package de.qaware.smartlabmeeting.business;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.result.ExtensionResult;
import de.qaware.smartlabcommons.result.ShiftResult;
import de.qaware.smartlabcommons.result.ShorteningResult;
import de.qaware.smartlabcore.generic.business.IEntityManagementBusinessLogic;

import java.time.Duration;

public interface IMeetingManagementBusinessLogic extends IEntityManagementBusinessLogic<IMeeting> {

    ShorteningResult shortenMeeting(String meetingId, Duration shortening);
    ExtensionResult extendMeeting(String meetingId, Duration extension);
    ShiftResult shiftMeeting(String meetingId, Duration shift);
}
