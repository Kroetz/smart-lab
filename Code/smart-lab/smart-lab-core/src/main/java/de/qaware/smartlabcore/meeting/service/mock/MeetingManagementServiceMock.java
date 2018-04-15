package de.qaware.smartlabcore.meeting.service.mock;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;
import de.qaware.smartlabcore.meeting.service.IMeetingManagementService;
import de.qaware.smartlabcore.workgroup.controller.IWorkgroupManagementApiClient;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mock")
@Slf4j
public class MeetingManagementServiceMock implements IMeetingManagementService {

    private final IMeetingConfigProviderMock meetingConfigProvider;
    private final IWorkgroupManagementApiClient workgroupManagementApiClient;

    public MeetingManagementServiceMock(
            @Qualifier("mock") IMeetingConfigProviderMock meetingConfigProvider,
            IWorkgroupManagementApiClient workgroupManagementApiClient) {
        this.meetingConfigProvider = meetingConfigProvider;
        this.workgroupManagementApiClient = workgroupManagementApiClient;
    }

    @Override
    public List<IMeeting> getMeetings() {
        return meetingConfigProvider.getMeetings();
    }

    @Override
    public Optional<IMeeting> getMeeting(long meetingId) {
        return meetingConfigProvider.getMeeting(meetingId);
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(Room room) {
        return meetingConfigProvider.getMeetings().stream()
                .filter(meeting -> meeting.getRoomId() == room.getId())
                .filter(meeting -> meeting.getStart().isBefore(Instant.now()))
                .filter(meeting -> meeting.getEnd().isAfter(Instant.now()))
                .findFirst();
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(Workgroup workgroup) {
        return meetingConfigProvider.getMeetings().stream()
                .filter(meeting -> meeting.getWorkgroupId() == workgroup.getId())
                .filter(meeting -> meeting.getStart().isBefore(Instant.now()))
                .filter(meeting -> meeting.getEnd().isAfter(Instant.now()))
                .findFirst();
    }

    @Override
    public Optional<IMeeting> getNextMeeting(Workgroup workgroup) {
        // Geht davon aus, dass Meetings nach Zeit sortiert sind!
        return meetingConfigProvider.getMeetings().stream()
                .filter(meeting -> meeting.getWorkgroupId() == workgroup.getId())
                .filter(meeting -> meeting.getStart().isAfter(Instant.now()))
                .findFirst();
    }

    @Override
    public boolean createMeeting(IMeeting meeting) {
        return meetingConfigProvider.createMeeting(meeting);
    }

    @Override
    public void shortenMeeting(long meetingId, Duration shortening) {
        val meeting = meetingConfigProvider.getMeeting(meetingId);
        if(meeting.isPresent()) {
            if(deleteMeeting(meetingId)) {
                val shortenedMeeting = meeting.get().copy();
                shortenedMeeting.setEnd(meeting.get().getEnd().minus(shortening));
                createMeeting(shortenedMeeting);
            }
        }
    }

    @Override
    public void shortenCurrentMeeting(Room room, Duration shortening) {
        val meeting = getCurrentMeeting(room);
        if(meeting.isPresent()) {
            shortenMeeting(meeting.get().getId(), shortening);
        }
    }

    @Override
    public void shortenNextMeeting(Workgroup workgroup, Duration shortening) {
        val nextMeeting = getNextMeeting(workgroup);
        if(nextMeeting.isPresent()) {
            shortenMeeting(nextMeeting.get().getId(), shortening);
        }
    }

    @Override
    public boolean extendMeeting(long meetingId, Duration extension) {
        val meeting = meetingConfigProvider.getMeeting(meetingId);
        if(meeting.isPresent()) {
            val extendedMeeting = meeting.get().copy();
            extendedMeeting.setEnd(meeting.get().getEnd().plus(extension));
            deleteMeeting(meetingId);
            if(createMeeting(extendedMeeting)) {
                return true;
            }
            else {
                createMeeting(meeting.get());
            }
        }
        return false;
    }

    @Override
    public boolean extendCurrentMeeting(Room room, Duration extension) {
        val extended = getCurrentMeeting(room)
                .map(meeting -> {
                    return extendMeeting(meeting.getId(), extension);
                })
                .orElse(false);
        return extended;
    }

    @Override
    public boolean extendCurrentMeeting(Workgroup workgroup, Duration extension) {
        val extended = getCurrentMeeting(workgroup)
                .map(meeting -> {
                    return extendMeeting(meeting.getId(), extension);
                })
                .orElse(false);
        return extended;
    }

    @Override
    public boolean extendNextMeeting(Workgroup workgroup, Duration extension) {
        val nextMeeting = getNextMeeting(workgroup);
        if(nextMeeting.isPresent()) {
            return extendMeeting(nextMeeting.get().getId(), extension);
        }
        return false;
    }

    @Override
    public boolean shiftMeeting(long meetingId, Duration shift) {
        val meeting = meetingConfigProvider.getMeeting(meetingId);
        if(meeting.isPresent()) {
            val shiftedMeeting = meeting.get().copy();
            shiftedMeeting.setStart(meeting.get().getStart().plus(shift));
            shiftedMeeting.setEnd(meeting.get().getEnd().plus(shift));
            deleteMeeting(meetingId);
            if(createMeeting(shiftedMeeting)) {
                return true;
            }
            else {
                createMeeting(meeting.get());
            }
        }
        return false;
    }

    @Override
    public boolean shiftCurrentMeeting(Room room, Duration shift) {
        val meeting = getCurrentMeeting(room);
        if(meeting.isPresent()) {
            return shiftMeeting(meeting.get().getId(), shift);
        }
        return false;
    }

    @Override
    public boolean shiftNextMeeting(Workgroup workgroup, Duration shift) {
        val nextMeeting = getNextMeeting(workgroup);
        if(nextMeeting.isPresent()) {
            return shiftMeeting(nextMeeting.get().getId(), shift);
        }
        return false;
    }

    @Override
    public boolean deleteMeeting(long meetingId) {
        return meetingConfigProvider.deleteMeeting(meetingId);
    }
}
