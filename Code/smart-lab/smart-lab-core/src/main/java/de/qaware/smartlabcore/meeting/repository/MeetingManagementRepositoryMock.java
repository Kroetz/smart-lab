package de.qaware.smartlabcore.meeting.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class MeetingManagementRepositoryMock implements IMeetingManagementRepository {

    private List<IMeeting> meetings;

    public MeetingManagementRepositoryMock(ISampleDataProvider sampleDataProvider) {
        this.meetings = new ArrayList<>(sampleDataProvider.getMeetings());
        sortMeetingsByStart();
    }

    private boolean exists(String meetingId) {
        return meetings.stream().anyMatch(m -> m.getId().equals(meetingId));
    }

    @Override
    public List<IMeeting> getMeetings() {
        return meetings;
    }

    @Override
    public Optional<IMeeting> getMeeting(String meetingId) {
        return meetings.stream()
                .filter(m -> m.getId().equals(meetingId))
                .findFirst();
    }

    @Override
    public boolean createMeeting(IMeeting meeting) {
        val meetingCollision = getMeetings().stream().anyMatch(m -> areMeetingsColliding(meeting, m));
        if(meetingCollision || exists(meeting.getId())) {
            return false;
        }
        meetings.add(meeting);
        sortMeetingsByStart();
        return true;
    }

    @Override
    public boolean deleteMeeting(String meetingId) {
        return meetings.removeAll(meetings.stream()
                .filter(meeting -> meeting.getId().equals(meetingId))
                .collect(Collectors.toList()));
    }

    @Override
    public void shortenMeeting(String meetingId, Duration shortening) {
        val meeting = getMeeting(meetingId);
        if(meeting.isPresent()) {
            if(deleteMeeting(meetingId)) {
                val shortenedMeeting = meeting.get().copy();
                shortenedMeeting.setEnd(meeting.get().getEnd().minus(shortening));
                createMeeting(shortenedMeeting);
            }
        }
    }

    @Override
    public boolean extendMeeting(String meetingId, Duration extension) {
        val meeting = getMeeting(meetingId);
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
    public boolean shiftMeeting(String meetingId, Duration shift) {
        val meeting = getMeeting(meetingId);
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

    private void sortMeetingsByStart() {
        meetings.sort((m1, m2) -> {
            if(m1.getStart().isBefore(m2.getStart())) {
                return -1;
            }
            if(m1.getStart().isAfter(m2.getStart())) {
                return 1;
            }
            return 0;
        });
    }

    private boolean areMeetingsColliding(IMeeting m1, IMeeting m2) {
        return (m1.getRoomId().equals(m2.getRoomId())
                && (m1.getStart().equals(m2.getStart()) && m1.getEnd().equals(m2.getEnd())
                || m1.getStart().isAfter(m2.getStart()) && m1.getStart().isBefore(m2.getEnd())
                || m1.getEnd().isAfter(m2.getStart()) && m1.getEnd().isBefore(m2.getEnd())
                || m2.getStart().isAfter(m1.getStart()) && m2.getStart().isBefore(m1.getEnd())
                || m2.getEnd().isAfter(m1.getStart()) && m2.getEnd().isBefore(m1.getEnd())));
    }
}
