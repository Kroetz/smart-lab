package de.qaware.smartlabcore.meeting.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IMeetingManagementRepository {

    List<IMeeting> getMeetings();
    Optional<IMeeting> getMeeting(String meetingId);

    boolean createMeeting(IMeeting meeting);

    boolean deleteMeeting(String meetingId);

    void shortenMeeting(String meetingId, Duration shortening);
    boolean extendMeeting(String meetingId, Duration extension);
    boolean shiftMeeting(String meetingId, Duration shift);
}
