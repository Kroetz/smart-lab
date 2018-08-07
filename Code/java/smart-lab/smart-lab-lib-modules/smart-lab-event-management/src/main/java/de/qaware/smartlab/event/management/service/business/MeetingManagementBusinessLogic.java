package de.qaware.smartlab.event.management.service.business;

import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.meeting.MeetingId;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlab.core.result.*;
import de.qaware.smartlab.event.management.service.repository.IMeetingManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class MeetingManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IMeeting, MeetingId> implements IMeetingManagementBusinessLogic {

    private final IMeetingManagementRepository meetingManagementRepository;
    private final Duration maxMeetingDuration;

    public MeetingManagementBusinessLogic(
            IMeetingManagementRepository meetingManagementRepository,
            // TODO: String literal
            @Qualifier("maxMeetingDuration") Duration maxMeetingDuration) {
        super(meetingManagementRepository);
        this.meetingManagementRepository = meetingManagementRepository;
        this.maxMeetingDuration = maxMeetingDuration;
    }

    @Override
    public Set<IMeeting> findAll(LocationId locationId) {
        return this.meetingManagementRepository.findAll(locationId);
    }

    @Override
    public Set<IMeeting> findAll(WorkgroupId workgroupId) {
        return this.meetingManagementRepository.findAll(workgroupId);
    }

    @Override
    public Set<IMeeting> findAllCurrent() {
        return this.meetingManagementRepository.findAllCurrent();
    }

    @Override
    public Optional<IMeeting> findCurrent(LocationId locationId) {
        return this.meetingManagementRepository.findCurrent(locationId);
    }

    @Override
    public Optional<IMeeting> findCurrent(WorkgroupId workgroupId) {
        return this.meetingManagementRepository.findCurrent(workgroupId);
    }

    @Override
    public ShorteningResult shortenMeeting(
            MeetingId meetingId,
            Duration shortening) {
        return findOne(meetingId).map(meeting -> {
            Duration shortenedDuration = meeting.getDuration().minus(shortening);
            if(shortenedDuration.isNegative() || shortenedDuration.isZero()) {
                return ShorteningResult.MINIMUM_REACHED;
            }
            return this.meetingManagementRepository.shortenMeeting(meeting, shortening);
        }).orElse(ShorteningResult.NOT_FOUND);
    }

    @Override
    public ExtensionResult extendMeeting(
            MeetingId meetingId,
            Duration extension) {
        return findOne(meetingId).map(meeting -> {
            if(meeting.getDuration().plus(extension).compareTo(this.maxMeetingDuration) > 0) {
                return ExtensionResult.MAXIMUM_REACHED_REACHED;
            }
            return this.meetingManagementRepository.extendMeeting(meeting, extension);
        }).orElse(ExtensionResult.NOT_FOUND);
    }

    @Override
    public ShiftResult shiftMeeting(
            MeetingId meetingId,
            Duration shift) {
        return findOne(meetingId)
                .map(meeting -> this.meetingManagementRepository.shiftMeeting(meeting, shift))
                .orElse(ShiftResult.NOT_FOUND);
    }
}
