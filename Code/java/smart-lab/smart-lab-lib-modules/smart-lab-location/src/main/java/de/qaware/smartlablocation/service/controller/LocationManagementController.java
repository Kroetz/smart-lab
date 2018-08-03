package de.qaware.smartlablocation.service.controller;

import de.qaware.smartlabcore.service.constant.location.LocationManagementApiConstants;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.location.dto.LocationDto;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.dto.MeetingDto;
import de.qaware.smartlabcore.service.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.service.controller.IBasicEntityManagementController;
import de.qaware.smartlabcore.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.service.url.IBaseUrlDetector;
import de.qaware.smartlablocation.service.business.ILocationManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

@Controller
@RequestMapping(LocationManagementApiConstants.MAPPING_BASE)
@Slf4j
public class LocationManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<ILocation, LocationDto> {

    private final ILocationManagementBusinessLogic locationManagementBusinessLogic;
    private final IDtoConverter<ILocation, LocationDto> locationConverter;
    private final IDtoConverter<IMeeting, MeetingDto> meetingConverter;

    public LocationManagementController(
            ILocationManagementBusinessLogic locationManagementBusinessLogic,
            IDtoConverter<ILocation, LocationDto> locationConverter,
            IDtoConverter<IMeeting, MeetingDto> meetingConverter) {
        this.locationManagementBusinessLogic = locationManagementBusinessLogic;
        this.locationConverter = locationConverter;
        this.meetingConverter = meetingConverter;
    }

    @Override
    @GetMapping(LocationManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<LocationDto> findAll() {
        return this.locationManagementBusinessLogic.findAll().stream()
                .map(this.locationConverter::toDto)
                .collect(toSet());
    }

    @Override
    @GetMapping(LocationManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<LocationDto> findOne(@PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId) {
        return responseFromEntityOptional(
                this.locationManagementBusinessLogic.findOne(LocationId.of(locationId)),
                this.locationConverter);
    }

    @Override
    @GetMapping(LocationManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    public ResponseEntity<Set<LocationDto>> findMultiple(@RequestParam(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_IDS) String[] locationIds) {
        return responseFromEntityOptionals(this.locationManagementBusinessLogic.findMultiple(stream(locationIds)
                        .map(LocationId::of)
                        .collect(toSet())),
                this.locationConverter);
    }

    @Override
    @PostMapping(value = LocationManagementApiConstants.MAPPING_CREATE_SINGLE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<LocationDto> create(@RequestBody LocationDto location) {
        ILocation locationToCreate = this.locationConverter.toEntity(location);
        LocationDto createdLocation = this.locationConverter.toDto(this.locationManagementBusinessLogic.create(locationToCreate));
        return ResponseEntity.ok(createdLocation);
    }

    @Override
    @PostMapping(value = LocationManagementApiConstants.MAPPING_CREATE_MULTIPLE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<LocationDto>> create(@RequestBody Set<LocationDto> locations) {
        return ResponseEntity.ok(this.locationManagementBusinessLogic.create(locations
                .stream()
                .map(this.locationConverter::toEntity)
                .collect(toSet()))
                .stream()
                .map(this.locationConverter::toDto)
                .collect(toSet()));
    }

    @Override
    @DeleteMapping(LocationManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId) {
        return this.locationManagementBusinessLogic.delete(LocationId.of(locationId)).toResponseEntity();
    }

    @GetMapping(LocationManagementApiConstants.MAPPING_GET_MEETINGS_AT_LOCATION)
    @ResponseBody
    public ResponseEntity<Set<MeetingDto>> getMeetingsAtLocation(@PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId) {
        return responseFromEntityOptionals(
                this.locationManagementBusinessLogic.getMeetingsAtLocation(LocationId.of(locationId)),
                this.meetingConverter);
    }

    @GetMapping(LocationManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    public ResponseEntity<MeetingDto> getCurrentMeeting(@PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId) {
        return responseFromEntityOptional(
                this.locationManagementBusinessLogic.getCurrentMeeting(LocationId.of(locationId)),
                this.meetingConverter);
    }

    @PostMapping(LocationManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    public ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(LocationManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        return this.locationManagementBusinessLogic.extendCurrentMeeting(LocationId.of(locationId), Duration.ofMinutes(extensionInMinutes)).toResponseEntity();
    }

    @RestController
    @RequestMapping(LocationManagementApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(LocationManagementApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
