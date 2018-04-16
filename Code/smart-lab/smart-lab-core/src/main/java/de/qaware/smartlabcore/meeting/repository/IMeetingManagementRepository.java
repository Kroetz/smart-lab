package de.qaware.smartlabcore.meeting.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IMeetingManagementRepository {

    List<IMeeting> getMeetings();
    Optional<IMeeting> getMeeting(long meetingId);

    boolean createMeeting(IMeeting meeting);

    boolean deleteMeeting(long meetingId);

    void shortenMeeting(long meetingId, Duration shortening);
    boolean extendMeeting(long meetingId, Duration extension);
    boolean shiftMeeting(long meetingId, Duration shift);
}
