package de.qaware.smartlabmeeting.repository;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.result.*;
import lombok.NonNull;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IMeetingManagementRepository {

    Map<RoomId, Set<IMeeting>> findAll();
    Set<IMeeting> findAll(RoomId roomId);
    Optional<IMeeting> findOne(MeetingId meetingId, RoomId roomId);
    Map<MeetingId, Optional<IMeeting>> findMultiple(Set<MeetingId> meetingIds, RoomId roomId);
    CreationResult create(IMeeting meeting);
    DeletionResult delete(MeetingId meetingId, RoomId roomId);
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
