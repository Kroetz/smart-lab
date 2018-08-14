package de.qaware.smartlab.api.service.client.event;

import de.qaware.smartlab.api.service.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlab.api.service.constant.event.EventManagementApiConstants;
import de.qaware.smartlab.core.data.event.EventDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

@FeignClient(name = EventManagementApiConstants.FEIGN_CLIENT_NAME, path = EventManagementApiConstants.MAPPING_BASE)
public interface IEventManagementApiClient extends IBasicEntityManagementApiClient<EventDto> {

    @Override
    @GetMapping(EventManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    Set<EventDto> findAll();

    @GetMapping(EventManagementApiConstants.MAPPING_FIND_ALL_BY_LOCATION_ID)
    @ResponseBody
    Set<EventDto> findAllByLocationId(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId);

    @GetMapping(EventManagementApiConstants.MAPPING_FIND_ALL_BY_WORKGROUP_ID)
    @ResponseBody
    Set<EventDto> findAllByWorkgroupId(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @GetMapping(EventManagementApiConstants.MAPPING_FIND_ALL_CURRENT)
    @ResponseBody
    Set<EventDto> findAllCurrent();

    @Override
    @GetMapping(EventManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    ResponseEntity<EventDto> findOne(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_EVENT_ID) String eventId);

    @Override
    @GetMapping(EventManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    ResponseEntity<Set<EventDto>> findMultiple(
            @RequestParam(EventManagementApiConstants.PARAMETER_NAME_EVENT_IDS) String[] eventIds);

    @GetMapping(EventManagementApiConstants.MAPPING_FIND_CURRENT_BY_LOCATION_ID)
    @ResponseBody
    ResponseEntity<EventDto> findCurrentByLocationId(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId);

    @GetMapping(EventManagementApiConstants.MAPPING_FIND_CURRENT_BY_WORKGROUP_ID)
    @ResponseBody
    ResponseEntity<EventDto> findCurrentByWorkgroupId(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @Override
    @PostMapping(value = EventManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<EventDto> create(@RequestBody EventDto event);

    @Override
    @PostMapping(value = EventManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Set<EventDto>> create(@RequestBody Set<EventDto> events);

    @Override
    @DeleteMapping(EventManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    ResponseEntity<Void> delete(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_EVENT_ID) String eventId);

    @PutMapping(EventManagementApiConstants.MAPPING_SHORTEN_EVENT)
    @ResponseBody
    ResponseEntity<Void> shortenEvent(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_EVENT_ID) String eventId,
            @RequestParam(EventManagementApiConstants.PARAMETER_NAME_SHORTENING_IN_MINUTES) long shorteningInMinutes);

    @PutMapping(EventManagementApiConstants.MAPPING_EXTEND_EVENT)
    @ResponseBody
    ResponseEntity<Void> extendEvent(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_EVENT_ID) String eventId,
            @RequestParam(EventManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes);

    @PutMapping(EventManagementApiConstants.MAPPING_SHIFT_EVENT)
    @ResponseBody
    ResponseEntity<Void> shiftEvent(
            @PathVariable(EventManagementApiConstants.PARAMETER_NAME_EVENT_ID) String eventId,
            @RequestParam(EventManagementApiConstants.PARAMETER_NAME_SHIFT_IN_MINUTES) long shiftInMinutes);

    @GetMapping(EventManagementApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
