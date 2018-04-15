package de.qaware.smartlabcore.meeting.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public interface IMeetingManagementService {

    List<IMeeting> getMeetings();
    Optional<IMeeting> getMeeting(long meetingId);
    Optional<IMeeting> getCurrentMeeting(Room room);
    Optional<IMeeting> getCurrentMeeting(Workgroup workgroup);
    Optional<IMeeting> getNextMeeting(Workgroup workgroup);

    boolean createMeeting(IMeeting meeting);

    boolean deleteMeeting(long meetingId);

    void shortenMeeting(long meetingId, Duration shortening);
    void shortenCurrentMeeting(Room room, Duration shortening);
    void shortenNextMeeting(Workgroup workgroup, Duration shortening);

    boolean extendMeeting(long meetingId, Duration extension);
    boolean extendCurrentMeeting(Room room, Duration extension);
    boolean extendCurrentMeeting(Workgroup workgroup, Duration extension);
    boolean extendNextMeeting(Workgroup workgroup, Duration extension);

    boolean shiftMeeting(long meetingId, Duration shift);
    boolean shiftCurrentMeeting(Room room, Duration shift);
    boolean shiftNextMeeting(Workgroup workgroup, Duration shift);
}
