package de.qaware.smartlabmeeting.repository;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.result.ShiftResult;
import de.qaware.smartlabcore.result.ShorteningResult;
import de.qaware.smartlabcore.generic.repository.IEntityManagementRepository;
import lombok.NonNull;

import java.time.Duration;

public interface IMeetingManagementRepository extends IEntityManagementRepository<IMeeting, MeetingId> {

    ShorteningResult shortenMeeting(@NonNull IMeeting meeting, Duration shortening);
    ExtensionResult extendMeeting(@NonNull IMeeting meeting, Duration extension);
    ShiftResult shiftMeeting(@NonNull IMeeting meeting, Duration shift);
}
