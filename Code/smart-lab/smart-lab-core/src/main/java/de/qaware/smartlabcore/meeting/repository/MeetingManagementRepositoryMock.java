package de.qaware.smartlabcore.meeting.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import de.qaware.smartlabcore.generic.repository.AbstractEntityManagementRepositoryMock;
import de.qaware.smartlabcore.generic.result.*;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;

@Repository
@Slf4j
public class MeetingManagementRepositoryMock extends AbstractEntityManagementRepositoryMock<IMeeting> implements IMeetingManagementRepository {

    public MeetingManagementRepositoryMock(ISampleDataProvider sampleDataProvider) {
        this.entities = new HashSet<>(sampleDataProvider.getMeetings());
    }
    
    @Override
    public CreationResult create(IMeeting meeting) {
        val meetingCollision = findAll().stream().anyMatch(m -> areMeetingsColliding(meeting, m));
        if(meetingCollision || exists(meeting.getId())) {
            return CreationResult.CONFLICT;
        }
        if(this.entities.add(meeting)) {
            return CreationResult.SUCCESS;
        }
        return CreationResult.ERROR;
    }

    @Override
    public ShorteningResult shortenMeeting(String meetingId, Duration shortening) {
        val meeting = findOne(meetingId);
        if(!meeting.isPresent()) {
            return ShorteningResult.NOT_FOUND;
        }
        if(delete(meetingId) == DeletionResult.SUCCESS) {
            val shortenedMeeting = meeting.get().copy();
            shortenedMeeting.setEnd(meeting.get().getEnd().minus(shortening));
            if(shortenedMeeting.getDuration().isNegative()) {
                return ShorteningResult.MINIMUM_REACHED;
            }
            val shortenedMeetingCreated =create(shortenedMeeting);
            if(shortenedMeetingCreated == CreationResult.SUCCESS) {
                return ShorteningResult.SUCCESS;
            }
        }
        return ShorteningResult.ERROR;
    }

    @Override
    public ExtensionResult extendMeeting(String meetingId, Duration extension) {
        val meeting = findOne(meetingId);
        if(!meeting.isPresent()) {
            return ExtensionResult.NOT_FOUND;
        }
        val extendedMeeting = meeting.get().copy();
        extendedMeeting.setEnd(meeting.get().getEnd().plus(extension));
        if(delete(meetingId) == DeletionResult.SUCCESS) {
            val extendedMeetingCreated = create(extendedMeeting);
            if(extendedMeetingCreated == CreationResult.CONFLICT) {
                create(meeting.get());
                return ExtensionResult.CONFLICT;
            }
            else if(extendedMeetingCreated == CreationResult.SUCCESS) {
                return ExtensionResult.SUCCESS;
            }
            else {
                create(meeting.get());
            }
        }
        return ExtensionResult.ERROR;
    }

    @Override
    public ShiftResult shiftMeeting(String meetingId, Duration shift) {
        val meeting = findOne(meetingId);
        if(!meeting.isPresent()) {
            return ShiftResult.NOT_FOUND;
        }
        val shiftedMeeting = meeting.get().copy();
        shiftedMeeting.setStart(meeting.get().getStart().plus(shift));
        shiftedMeeting.setEnd(meeting.get().getEnd().plus(shift));
        if(delete(meetingId) == DeletionResult.SUCCESS) {
            val shiftedMeetingCreated = create(shiftedMeeting);
            if(shiftedMeetingCreated == CreationResult.CONFLICT) {
                create(meeting.get());
                return ShiftResult.CONFLICT;
            }
            else if(shiftedMeetingCreated == CreationResult.SUCCESS) {
                return ShiftResult.SUCCESS;
            }
            else {
                create(meeting.get());
            }
        }
        return ShiftResult.ERROR;
    }

    private void sortMeetingsByStart(List<IMeeting> meetings) {
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
