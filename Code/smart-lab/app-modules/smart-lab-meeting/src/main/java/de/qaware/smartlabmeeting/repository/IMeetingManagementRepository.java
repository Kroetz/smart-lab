package de.qaware.smartlabmeeting.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.result.ExtensionResult;
import de.qaware.smartlabcommons.result.ShiftResult;
import de.qaware.smartlabcommons.result.ShorteningResult;
import de.qaware.smartlabcore.generic.repository.IEntityManagementRepository;
import lombok.NonNull;

import java.time.Duration;

public interface IMeetingManagementRepository extends IEntityManagementRepository<IMeeting> {

    ShorteningResult shortenMeeting(@NonNull IMeeting meeting, Duration shortening);
    ExtensionResult extendMeeting(@NonNull IMeeting meeting, Duration extension);
    ShiftResult shiftMeeting(@NonNull IMeeting meeting, Duration shift);
}
