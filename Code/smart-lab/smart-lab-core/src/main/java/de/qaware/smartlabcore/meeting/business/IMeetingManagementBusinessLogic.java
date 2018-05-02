package de.qaware.smartlabcore.meeting.business;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import de.qaware.smartlabcore.generic.result.ShiftResult;
import de.qaware.smartlabcore.generic.result.ShorteningResult;
import de.qaware.smartlabcore.generic.business.IEntityManagementBusinessLogic;

import java.time.Duration;

public interface IMeetingManagementBusinessLogic extends IEntityManagementBusinessLogic<IMeeting> {

    ShorteningResult shortenMeeting(String meetingId, Duration shortening);
    ExtensionResult extendMeeting(String meetingId, Duration extension);
    ShiftResult shiftMeeting(String meetingId, Duration shift);
}
