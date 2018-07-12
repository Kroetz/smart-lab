package de.qaware.smartlabmeeting.service.repository;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.generic.repository.IBasicEntityManagementRepository;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.result.ShiftResult;
import de.qaware.smartlabcore.result.ShorteningResult;
import lombok.NonNull;

import java.time.Duration;
import java.util.Set;

public interface IMeetingManagementRepository extends IBasicEntityManagementRepository<IMeeting, MeetingId> {

    Set<IMeeting> findAll(RoomId roomId);
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
