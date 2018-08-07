package de.qaware.smartlabeventmanagement.service.repository;

import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.meeting.MeetingId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.result.ExtensionResult;
import de.qaware.smartlab.core.result.ShiftResult;
import de.qaware.smartlab.core.result.ShorteningResult;
import de.qaware.smartlab.core.service.repository.IBasicEntityManagementRepository;
import lombok.NonNull;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IMeetingManagementRepository extends IBasicEntityManagementRepository<IMeeting, MeetingId> {

    Set<IMeeting> findAll(LocationId locationId);
    Set<IMeeting> findAll(WorkgroupId workgroupId);
    Set<IMeeting> findAllCurrent();
    Optional<IMeeting> findCurrent(LocationId locationId);
    Optional<IMeeting> findCurrent(WorkgroupId workgroupId);
    ShorteningResult shortenMeeting(
            @NonNull IMeeting meeting,
            Duration shortening);
    ExtensionResult extendMeeting(
            @NonNull IMeeting meeting,
            Duration extension);
    ShiftResult shiftMeeting(
            @NonNull IMeeting meeting,
            Duration shift);
}
