package de.qaware.smartlablocationmanagement.service.business;

import de.qaware.smartlab.api.service.connector.meeting.IMeetingManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlablocationmanagement.service.repository.ILocationManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class LocationManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<ILocation, LocationId> implements ILocationManagementBusinessLogic {

    private final ILocationManagementRepository locationManagementRepository;
    private final IMeetingManagementService meetingManagementService;

    public LocationManagementBusinessLogic(
            ILocationManagementRepository locationManagementRepository,
            IMeetingManagementService meetingManagementService) {
        super(locationManagementRepository);
        this.locationManagementRepository = locationManagementRepository;
        this.meetingManagementService = meetingManagementService;
    }

    @Override
    public Optional<Set<IMeeting>> getMeetingsAtLocation(LocationId locationId) {
        return this.locationManagementRepository.findOne(locationId)
                .map(location -> Optional.of(this.meetingManagementService.findAll(location.getId())))
                .orElse(Optional.empty());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(LocationId locationId) {
        return this.locationManagementRepository.findOne(locationId)
                .map(location -> Optional.of(this.meetingManagementService.findCurrent(location.getId())))
                .orElse(Optional.empty());
    }

    @Override
    public ExtensionResult extendCurrentMeeting(LocationId locationId, Duration extension) {
        return this.locationManagementRepository.findOne(locationId)
                .map(location -> {
                    try {
                        return getCurrentMeeting(location.getId())
                                .map(meeting -> {
                                    this.meetingManagementService.extendMeeting(meeting.getId(), extension);
                                    return ExtensionResult.SUCCESS;})
                                .orElse(ExtensionResult.NOT_FOUND);
                    }
                    catch(Exception e) {
                        return ExtensionResult.fromException(e);
                    }
                })
                .orElse(ExtensionResult.NOT_FOUND);
    }
}
