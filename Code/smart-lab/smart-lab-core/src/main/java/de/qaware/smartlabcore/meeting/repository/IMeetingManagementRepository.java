package de.qaware.smartlabcore.meeting.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.generic.result.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IMeetingManagementRepository {

    List<IMeeting> getMeetings();
    Optional<IMeeting> getMeeting(String meetingId);

    CreationResult createMeeting(IMeeting meeting);

    DeletionResult deleteMeeting(String meetingId);

    ShorteningResult shortenMeeting(String meetingId, Duration shortening);
    ExtensionResult extendMeeting(String meetingId, Duration extension);
    ShiftResult shiftMeeting(String meetingId, Duration shift);
}
