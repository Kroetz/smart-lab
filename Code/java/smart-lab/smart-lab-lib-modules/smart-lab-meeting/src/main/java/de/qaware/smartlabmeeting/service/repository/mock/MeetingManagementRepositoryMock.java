package de.qaware.smartlabmeeting.service.repository.mock;

import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityCreationException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.result.DeletionResult;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.result.ShiftResult;
import de.qaware.smartlabcore.result.ShorteningResult;
import de.qaware.smartlabcore.service.repository.AbstractBasicEntityManagementRepositoryMock;
import de.qaware.smartlabmeeting.service.repository.IMeetingManagementRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;

import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Repository
@ConditionalOnProperty(
        prefix = Property.Prefix.MEETING_MANAGEMENT_REPOSITORY,
        name = Property.Name.MEETING_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.MeetingManagementRepository.MOCK)
@Slf4j
public class MeetingManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<IMeeting, MeetingId> implements IMeetingManagementRepository {

    private Map<LocationId, Set<IMeeting>> meetingsByLocation;

    public MeetingManagementRepositoryMock(Set<IMeeting> initialMeetings) {
        super(initialMeetings);
        this.meetingsByLocation = new HashMap<>();
    }

    @Override
    public Set<IMeeting> findAll() {
        Set<IMeeting> allMeetings = new HashSet<>();
        this.meetingsByLocation.keySet()
                .forEach(locationId -> allMeetings.addAll(this.meetingsByLocation.get(locationId)));
        return allMeetings;
    }

    @Override
    public Set<IMeeting> findAll(LocationId locationId) {
        Set<IMeeting> meetings = this.meetingsByLocation.get(locationId);
        return isNull(meetings) ? emptySet() : meetings;
    }

    @Override
    public Set<IMeeting> findAll(WorkgroupId workgroupId) {
        return findAll().stream()
                .filter(meeting -> meeting.getWorkgroupId().equals(workgroupId))
                .collect(toSet());
    }

    @Override
    public Set<IMeeting> findAllCurrent() {
        return findAll().stream()
                .filter(IMeeting::isInProgress)
                .collect(toSet());
    }

    @Override
    public Optional<IMeeting> findOne(MeetingId meetingId) {
        Set<IMeeting> meetingsAtLocation = this.meetingsByLocation.get(meetingId.getLocationIdPart());
        return isNull(meetingsAtLocation) ? Optional.empty() : meetingsAtLocation.stream()
                .filter(entity -> entity.getId().equals(meetingId))
                .findFirst();
    }

    @Override
    public Map<MeetingId, Optional<IMeeting>> findMultiple(Set<MeetingId> meetingIds) {
        Map<MeetingId, Optional<IMeeting>> meetingsById = new HashMap<>();
        meetingIds.forEach(meetingId -> meetingsById.put(meetingId, findOne(meetingId)));
        return meetingsById;
    }

    @Override
    public Optional<IMeeting> findCurrent(LocationId locationId) {
        return findAll(locationId).stream()
                .filter(IMeeting::isInProgress)
                .findFirst();
    }

    @Override
    public Optional<IMeeting> findCurrent(WorkgroupId workgroupId) {
        return findAll().stream()
                .filter(meeting -> meeting.getWorkgroupId().equals(workgroupId))
                .filter(IMeeting::isInProgress)
                .findFirst();
    }

    @Override
    public synchronized IMeeting create(IMeeting meeting) {
        boolean meetingCollision = findAll(meeting.getLocationId()).stream().anyMatch(m -> m.isColliding(meeting));
        if(meetingCollision || exists(meeting.getId())) {
            log.error("Cannot create meeting {} because a meeting with that ID already exists", meeting);
            // TODO: Meaningful exception message
            throw new EntityConflictException();
        }
        Set<IMeeting> meetingsAtLocation = this.meetingsByLocation.get(meeting.getLocationId());
        if(isNull(meetingsAtLocation)) {
            meetingsAtLocation = new HashSet<>();
            this.meetingsByLocation.put(meeting.getLocationId(), meetingsAtLocation);
        }
        if(meetingsAtLocation.add(meeting)) {
            return meeting;
        }
        log.error("Cannot create meeting {} because of unknown reason", meeting);
        // TODO: Meaningful exception message
        throw new EntityCreationException();
    }

    @Override
    public synchronized DeletionResult delete(MeetingId meetingId) {
        Set<IMeeting> meetingsAtLocation = this.meetingsByLocation.get(meetingId.getLocationIdPart());
        List<IMeeting> meetingsToDelete = isNull(meetingsAtLocation) ? new ArrayList<>() : meetingsAtLocation.stream()
                .filter(meeting -> meeting.getId().equals(meetingId))
                .collect(toList());
        if(isNull(meetingsAtLocation) || meetingsToDelete.isEmpty()) {
            return DeletionResult.NOT_FOUND;
        }
        boolean deleted =  meetingsAtLocation.removeAll(meetingsToDelete);
        if(deleted) {
            return DeletionResult.SUCCESS;
        }
        return DeletionResult.ERROR;
    }

    @Override
    public synchronized ShorteningResult shortenMeeting(@NonNull IMeeting meeting, Duration shortening) {
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            IMeeting shortenedMeeting = meeting.withEnd(meeting.getEnd().minus(shortening));
            try {
                create(shortenedMeeting);
                return ShorteningResult.SUCCESS;
            }
            catch(Exception e) {
                return ShorteningResult.ERROR;
            }
        }
        return ShorteningResult.ERROR;
    }

    @Override
    public synchronized ExtensionResult extendMeeting(@NonNull IMeeting meeting, Duration extension) {
        IMeeting extendedMeeting = meeting.withEnd(meeting.getEnd().plus(extension));
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            try {
                create(extendedMeeting);
                return ExtensionResult.SUCCESS;
            }
            catch(EntityConflictException e) {
                create(meeting);
                return ExtensionResult.CONFLICT;
            }
            catch(Exception e) {
                create(meeting);
                return ExtensionResult.ERROR;
            }
        }
        return ExtensionResult.ERROR;
    }

    @Override
    public synchronized ShiftResult shiftMeeting(@NonNull IMeeting meeting, Duration shift) {
        IMeeting shiftedMeeting = meeting.withStartAndEnd(
                meeting.getStart().plus(shift),
                meeting.getEnd().plus(shift));
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            try {
                create(shiftedMeeting);
                return ShiftResult.SUCCESS;
            }
            catch(EntityConflictException e) {
                create(meeting);
                return ShiftResult.CONFLICT;
            }
            catch(Exception e) {
                create(meeting);
                return ShiftResult.ERROR;
            }
        }
        return ShiftResult.ERROR;
    }
}
