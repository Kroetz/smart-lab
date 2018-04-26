package de.qaware.smartlabcore.meeting.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.generic.repository.IEntityManagementRepository;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import de.qaware.smartlabcore.generic.result.ShiftResult;
import de.qaware.smartlabcore.generic.result.ShorteningResult;

import java.time.Duration;

public interface IMeetingManagementRepository extends IEntityManagementRepository<IMeeting> {

    ShorteningResult shortenMeeting(String meetingId, Duration shortening);
    ExtensionResult extendMeeting(String meetingId, Duration extension);
    ShiftResult shiftMeeting(String meetingId, Duration shift);
}
