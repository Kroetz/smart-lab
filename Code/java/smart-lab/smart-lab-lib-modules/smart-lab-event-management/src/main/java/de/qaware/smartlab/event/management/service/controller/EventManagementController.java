package de.qaware.smartlab.event.management.service.controller;

import de.qaware.smartlab.api.service.constant.event.EventManagementApiConstants;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.EntityNotFoundException;
import de.qaware.smartlab.core.service.controller.AbstractSmartLabController;
import de.qaware.smartlab.core.service.controller.IBasicEntityManagementController;
import de.qaware.smartlab.core.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlab.core.service.url.IBaseUrlDetector;
import de.qaware.smartlab.event.management.service.business.IEventManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

import static java.time.Duration.ofMinutes;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping(EventManagementApiConstants.MAPPING_BASE)
@Slf4j
public class EventManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<IEvent, EventDto> {

    private final IEventManagementBusinessLogic eventManagementBusinessLogic;
    private final IDtoConverter<IEvent, EventDto> eventConverter;

    public EventManagementController(
            IEventManagementBusinessLogic eventManagementBusinessLogic,
            IDtoConverter<IEvent, EventDto> eventConverter) {
        this.eventManagementBusinessLogic = eventManagementBusinessLogic;
        this.eventConverter = eventConverter;
    }

    @Override
    @GetMapping(EventManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<EventDto> findAll() {
        return this.eventManagementBusinessLogic.findAll().stream()
                .map(this.eventConverter::toDto)
                .collect(toSet());
    }

    @GetMapping(EventManagementApiConstants.MAPPING_FIND_ALL_BY_LOCATION_ID)
    @ResponseBody
    public Set<EventDto> findAllByLocationId(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId) {
        return this.eventManagementBusinessLogic.findAll(LocationId.of(locationId)).stream()
                .map(this.eventConverter::toDto)
                .collect(toSet());
    }

    @GetMapping(EventManagementApiConstants.MAPPING_FIND_ALL_BY_WORKGROUP_ID)
    @ResponseBody
    public Set<EventDto> findAllByWorkgroupId(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return this.eventManagementBusinessLogic.findAll(WorkgroupId.of(workgroupId)).stream()
                .map(this.eventConverter::toDto)
                .collect(toSet());
    }

    @GetMapping(EventManagementApiConstants.MAPPING_FIND_ALL_CURRENT)
    @ResponseBody
    public Set<EventDto> findAllCurrent() {
        return this.eventManagementBusinessLogic.findAllCurrent().stream()
                .map(this.eventConverter::toDto)
                .collect(toSet());
    }

    @Override
    @GetMapping(EventManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<EventDto> findOne(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_EVENT_ID) String eventId) {
        return responseFromEntityOptional(
                this.eventManagementBusinessLogic.findOne(EventId.of(eventId)),
                this.eventConverter);
    }

    @Override
    @GetMapping(EventManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    public ResponseEntity<Set<EventDto>> findMultiple(
            @RequestParam(EventManagementApiConstants.PARAMETER_NAME_EVENT_IDS) String[] eventIds) {
        return responseFromEntityOptionals(
                this.eventManagementBusinessLogic.findMultiple(stream(eventIds)
                        .map(EventId::of)
                        .collect(toSet())),
                this.eventConverter);
    }

    @GetMapping(EventManagementApiConstants.MAPPING_FIND_CURRENT_BY_LOCATION_ID)
    @ResponseBody
    public ResponseEntity<EventDto> findCurrentByLocationId(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId) {
        return responseFromEntityOptional(
                this.eventManagementBusinessLogic.findCurrent(LocationId.of(locationId)),
                this.eventConverter);
    }

    @GetMapping(EventManagementApiConstants.MAPPING_FIND_CURRENT_BY_WORKGROUP_ID)
    @ResponseBody
    public ResponseEntity<EventDto> findCurrentByWorkgroupId(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromEntityOptional(
                this.eventManagementBusinessLogic.findCurrent(WorkgroupId.of(workgroupId)),
                this.eventConverter);
    }

    @Override
    @PostMapping(value = EventManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<EventDto> create(@RequestBody EventDto event) {
        IEvent eventToCreate = this.eventConverter.toEntity(event);
        EventDto createdEvent = this.eventConverter.toDto(this.eventManagementBusinessLogic.create(eventToCreate));
        return ResponseEntity.ok(createdEvent);
    }

    @Override
    @PostMapping(value = EventManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<EventDto>> create(Set<EventDto> events) {
        return ResponseEntity.ok(this.eventManagementBusinessLogic.create(events
                .stream()
                .map(this.eventConverter::toEntity)
                .collect(toSet()))
                .stream()
                .map(this.eventConverter::toDto)
                .collect(toSet()));
    }

    @Override
    @DeleteMapping(EventManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    public ResponseEntity<Void> delete(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_EVENT_ID) String eventId) {
        this.eventManagementBusinessLogic.delete(EventId.of(eventId));
        return ResponseEntity.ok().build();
    }

    @PutMapping(EventManagementApiConstants.MAPPING_SHORTEN_EVENT)
    @ResponseBody
    public ResponseEntity<Void> shortenEvent(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_EVENT_ID) String eventId,
            @RequestParam(EventManagementApiConstants.PARAMETER_NAME_SHORTENING_IN_MINUTES) long shorteningInMinutes) {
        this.eventManagementBusinessLogic.shortenEvent(
                EventId.of(eventId),
                ofMinutes(shorteningInMinutes));
        return ResponseEntity.ok().build();
    }

    @PutMapping(EventManagementApiConstants.MAPPING_EXTEND_EVENT)
    @ResponseBody
    public ResponseEntity<Void> extendEvent(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_EVENT_ID) String eventId,
            @RequestParam(EventManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        this.eventManagementBusinessLogic.extendEvent(
                EventId.of(eventId),
                ofMinutes(extensionInMinutes));
        return ResponseEntity.ok().build();
    }

    @PutMapping(EventManagementApiConstants.MAPPING_SHIFT_EVENT)
    @ResponseBody
    public ResponseEntity<Void> shiftEvent(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_EVENT_ID) String eventId,
            @RequestParam(EventManagementApiConstants.PARAMETER_NAME_SHIFT_IN_MINUTES) long shiftInMinutes) {
        return this.eventManagementBusinessLogic.shiftEvent(
                EventId.of(eventId),
                ofMinutes(shiftInMinutes)).toResponseEntity();
    }

    @RestController
    @RequestMapping(EventManagementApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(EventManagementApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
