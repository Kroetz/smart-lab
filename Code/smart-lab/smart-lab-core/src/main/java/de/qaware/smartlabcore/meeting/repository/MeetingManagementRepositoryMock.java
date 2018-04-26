package de.qaware.smartlabcore.meeting.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import de.qaware.smartlabcore.generic.repository.AbstractEntityManagementRepositoryMock;
import de.qaware.smartlabcore.generic.result.*;
import lombok.NonNull;
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
    public ShorteningResult shortenMeeting(@NonNull IMeeting meeting, Duration shortening) {
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            val shortenedMeeting = meeting.copy();
            shortenedMeeting.setEnd(meeting.getEnd().minus(shortening));
            val shortenedMeetingCreated = create(shortenedMeeting);
            if(shortenedMeetingCreated == CreationResult.SUCCESS) {
                return ShorteningResult.SUCCESS;
            }
        }
        return ShorteningResult.ERROR;
    }

    @Override
    public ExtensionResult extendMeeting(@NonNull IMeeting meeting, Duration extension) {
        val extendedMeeting = meeting.copy();
        extendedMeeting.setEnd(meeting.getEnd().plus(extension));
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            val extendedMeetingCreated = create(extendedMeeting);
            if(extendedMeetingCreated == CreationResult.CONFLICT) {
                create(meeting);
                return ExtensionResult.CONFLICT;
            }
            else if(extendedMeetingCreated == CreationResult.SUCCESS) {
                return ExtensionResult.SUCCESS;
            }
            else {
                create(meeting);
            }
        }
        return ExtensionResult.ERROR;
    }

    @Override
    public ShiftResult shiftMeeting(@NonNull IMeeting meeting, Duration shift) {
        val shiftedMeeting = meeting.copy();
        shiftedMeeting.setStart(meeting.getStart().plus(shift));
        shiftedMeeting.setEnd(meeting.getEnd().plus(shift));
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            val shiftedMeetingCreated = create(shiftedMeeting);
            if(shiftedMeetingCreated == CreationResult.CONFLICT) {
                create(meeting);
                return ShiftResult.CONFLICT;
            }
            else if(shiftedMeetingCreated == CreationResult.SUCCESS) {
                return ShiftResult.SUCCESS;
            }
            else {
                create(meeting);
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
