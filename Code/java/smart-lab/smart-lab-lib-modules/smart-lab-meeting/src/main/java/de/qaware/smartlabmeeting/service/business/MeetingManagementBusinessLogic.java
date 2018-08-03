package de.qaware.smartlabmeeting.service.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlabcore.miscellaneous.Constants;
import de.qaware.smartlabcore.result.*;
import de.qaware.smartlabmeeting.service.repository.IMeetingManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class MeetingManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IMeeting, MeetingId> implements IMeetingManagementBusinessLogic {

    private final IMeetingManagementRepository meetingManagementRepository;

    public MeetingManagementBusinessLogic(IMeetingManagementRepository meetingManagementRepository) {
        super(meetingManagementRepository);
        this.meetingManagementRepository = meetingManagementRepository;
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
            if(meeting.getDuration().plus(extension).compareTo(Constants.MAXIMAL_MEETING_DURATION) > 0) {
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
