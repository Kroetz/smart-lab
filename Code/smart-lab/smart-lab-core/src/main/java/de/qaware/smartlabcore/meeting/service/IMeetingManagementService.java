package de.qaware.smartlabcore.meeting.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.generic.result.*;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IMeetingManagementService {

    Set<IMeeting> getMeetings();
    Optional<IMeeting> getMeeting(String meetingId);

    CreationResult createMeeting(IMeeting meeting);

    DeletionResult deleteMeeting(String meetingId);

    ShorteningResult shortenMeeting(String meetingId, Duration shortening);
    ExtensionResult extendMeeting(String meetingId, Duration extension);
    ShiftResult shiftMeeting(String meetingId, Duration shift);
}
