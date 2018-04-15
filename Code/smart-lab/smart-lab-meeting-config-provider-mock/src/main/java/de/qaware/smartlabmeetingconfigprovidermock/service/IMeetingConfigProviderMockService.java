package de.qaware.smartlabmeetingconfigprovidermock.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;

import java.util.List;
import java.util.Optional;

public interface IMeetingConfigProviderMockService {

    boolean exists(long meetingId);
    List<IMeeting> getMeetings();
    Optional<IMeeting> getMeeting(long meetingId);
    boolean createMeeting(IMeeting meeting);
    boolean deleteMeeting(long meetingId);
}
