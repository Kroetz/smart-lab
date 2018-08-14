package de.qaware.smartlab.location.management.service.controller;

import de.qaware.smartlab.api.service.constant.location.LocationManagementApiConstants;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationDto;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.service.controller.AbstractSmartLabController;
import de.qaware.smartlab.core.service.controller.IBasicEntityManagementController;
import de.qaware.smartlab.core.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlab.core.service.url.IBaseUrlDetector;
import de.qaware.smartlab.location.management.service.business.ILocationManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

import static java.time.Duration.ofMinutes;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

@Controller
@RequestMapping(LocationManagementApiConstants.MAPPING_BASE)
@Slf4j
public class LocationManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<LocationDto> {

    private final ILocationManagementBusinessLogic locationManagementBusinessLogic;
    private final IDtoConverter<ILocation, LocationDto> locationConverter;
    private final IDtoConverter<IEvent, EventDto> eventConverter;

    public LocationManagementController(
            ILocationManagementBusinessLogic locationManagementBusinessLogic,
            IDtoConverter<ILocation, LocationDto> locationConverter,
            IDtoConverter<IEvent, EventDto> eventConverter) {
        this.locationManagementBusinessLogic = locationManagementBusinessLogic;
        this.locationConverter = locationConverter;
        this.eventConverter = eventConverter;
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
        this.locationManagementBusinessLogic.delete(LocationId.of(locationId));
        return ResponseEntity.ok().build();
    }

    @GetMapping(LocationManagementApiConstants.MAPPING_GET_EVENTS_AT_LOCATION)
    @ResponseBody
    public ResponseEntity<Set<EventDto>> getEventsAtLocation(@PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId) {
        return responseFromEntityOptionals(
                this.locationManagementBusinessLogic.getEventsAtLocation(LocationId.of(locationId)),
                this.eventConverter);
    }

    @GetMapping(LocationManagementApiConstants.MAPPING_GET_CURRENT_EVENT)
    @ResponseBody
    public ResponseEntity<EventDto> getCurrentEvent(@PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId) {
        return responseFromEntityOptional(
                this.locationManagementBusinessLogic.getCurrentEvent(LocationId.of(locationId)),
                this.eventConverter);
    }

    @PostMapping(LocationManagementApiConstants.MAPPING_EXTEND_CURRENT_EVENT)
    @ResponseBody
    public ResponseEntity<Void> extendCurrentEvent(
            @PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(LocationManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        this.locationManagementBusinessLogic.extendCurrentEvent(LocationId.of(locationId), ofMinutes(extensionInMinutes));
        return ResponseEntity.ok().build();
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
