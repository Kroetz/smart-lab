package de.qaware.smartlabcore.meeting.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.sample.ISampleDataFactory;
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

    public MeetingManagementRepositoryMock(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory) {
        this.meetings = new ArrayList<>();
        this.meetings.addAll(new ArrayList<>(coastGuardDataFactory.createMeetings().values()));
        this.meetings.addAll(new ArrayList<>(forestRangersDataFactory.createMeetings().values()));
        this.meetings.addAll(new ArrayList<>(fireFightersDataFactory.createMeetings().values()));
        sortMeetingsByStart();
    }

    private boolean exists(long meetingId) {
        return meetings.stream().anyMatch(m -> m.getId() == meetingId);
    }

    @Override
    public List<IMeeting> getMeetings() {
        return meetings;
    }

    @Override
    public Optional<IMeeting> getMeeting(long meetingId) {
        return meetings.stream()
                .filter(m -> m.getId() == meetingId)
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
    public boolean deleteMeeting(long meetingId) {
        return meetings.removeAll(meetings.stream()
                .filter(meeting -> meeting.getId() == meetingId)
                .collect(Collectors.toList()));
    }

    @Override
    public void shortenMeeting(long meetingId, Duration shortening) {
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
    public boolean extendMeeting(long meetingId, Duration extension) {
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
    public boolean shiftMeeting(long meetingId, Duration shift) {
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
        return (m1.getRoomId() == m2.getRoomId()
                && (m1.getStart().equals(m2.getStart()) && m1.getEnd().equals(m2.getEnd())
                || m1.getStart().isAfter(m2.getStart()) && m1.getStart().isBefore(m2.getEnd())
                || m1.getEnd().isAfter(m2.getStart()) && m1.getEnd().isBefore(m2.getEnd())
                || m2.getStart().isAfter(m1.getStart()) && m2.getStart().isBefore(m1.getEnd())
                || m2.getEnd().isAfter(m1.getStart()) && m2.getEnd().isBefore(m1.getEnd())));
    }
}
