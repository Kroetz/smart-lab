package de.qaware.smartlab.microservice.service.connector.meeting;

import de.qaware.smartlab.api.service.client.meeting.IMeetingManagementApiClient;
import de.qaware.smartlab.api.service.connector.meeting.IMeetingManagementService;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.meeting.MeetingDto;
import de.qaware.smartlab.core.data.meeting.MeetingId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.*;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.microservice.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class MeetingManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<IMeeting, MeetingId, MeetingDto> implements IMeetingManagementService {

    private final IMeetingManagementApiClient meetingManagementApiClient;

    public MeetingManagementMicroserviceConnector(
            IMeetingManagementApiClient meetingManagementApiClient,
            IDtoConverter<IMeeting, MeetingDto> meetingConverter) {
        super(meetingManagementApiClient, meetingConverter);
        this.meetingManagementApiClient = meetingManagementApiClient;
    }

    @Override
    public Set<IMeeting> findAll(LocationId locationId) {
        try {
            return this.meetingManagementApiClient.findAllByLocationId(locationId.getIdValue()).stream()
                    .map(this.converter::toEntity)
                    .collect(toSet());
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public Set<IMeeting> findAll(WorkgroupId workgroupId) {
        try {
            return this.meetingManagementApiClient.findAllByWorkgroupId(workgroupId.getIdValue()).stream()
                    .map(this.converter::toEntity)
                    .collect(toSet());
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public Set<IMeeting> findAllCurrent() {
        try {
            return this.meetingManagementApiClient.findAllCurrent().stream()
                    .map(this.converter::toEntity)
                    .collect(toSet());
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IMeeting findCurrent(LocationId locationId) {
        try {
            MeetingDto currentMeeting = this.meetingManagementApiClient.findCurrentByLocationId(locationId.getIdValue()).getBody();
            requireNonNull(currentMeeting);
            return this.converter.toEntity(currentMeeting);
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public IMeeting findCurrent(WorkgroupId workgroupId) {
        try {
            MeetingDto currentMeeting = this.meetingManagementApiClient.findCurrentByWorkgroupId(workgroupId.getIdValue()).getBody();
            requireNonNull(currentMeeting);
            return this.converter.toEntity(currentMeeting);
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void shortenMeeting(MeetingId meetingId, Duration shortening)
            throws EntityNotFoundException, MinimalDurationReachedException, UnknownErrorException {
        try {
            this.meetingManagementApiClient.shortenMeeting(
                    meetingId.getIdValue(),
                    shortening.toMinutes());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            if(e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                throw new MinimalDurationReachedException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void extendMeeting(MeetingId meetingId, Duration extension) {
        try {
            this.meetingManagementApiClient.extendMeeting(
                    meetingId.getIdValue(),
                    extension.toMinutes());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            if(e.status() == HttpStatus.CONFLICT.value()) {
                // TODO: Incorporate information about the conflict
                throw new EntityConflictException();
            }
            if(e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                throw new MaximalDurationReachedException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void shiftMeeting(MeetingId meetingId, Duration shift) {
        try {
            this.meetingManagementApiClient.shiftMeeting(
                    meetingId.getIdValue(),
                    shift.toMinutes());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            if(e.status() == HttpStatus.CONFLICT.value()) {
                // TODO: Incorporate information about the conflict
                throw new EntityConflictException();
            }
            throw new UnknownErrorException();
        }
    }

    @Component
    // TODO: String literal
    @Qualifier("meetingManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IMeetingManagementApiClient meetingManagementApiClient;

        public BaseUrlGetter(IMeetingManagementApiClient meetingManagementApiClient) {
            this.meetingManagementApiClient = meetingManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            // TODO: Exceptions
            try {
                return this.meetingManagementApiClient.getBaseUrl().getBody();
            }
            catch(RetryableException e) {
                throw e;
            }
            catch(FeignException e) {
                throw new UnknownErrorException();
            }
        }
    }
}
