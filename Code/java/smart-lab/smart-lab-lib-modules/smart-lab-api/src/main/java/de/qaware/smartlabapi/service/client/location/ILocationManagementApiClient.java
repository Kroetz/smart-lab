package de.qaware.smartlabapi.service.client.location;

import de.qaware.smartlabapi.service.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlabapi.service.constant.location.LocationManagementApiConstants;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.dto.LocationDto;
import de.qaware.smartlabcore.data.meeting.dto.MeetingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

@FeignClient(name = LocationManagementApiConstants.FEIGN_CLIENT_NAME, path = LocationManagementApiConstants.MAPPING_BASE)
public interface ILocationManagementApiClient extends IBasicEntityManagementApiClient<ILocation, LocationDto> {

    @Override
    @GetMapping(LocationManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    Set<LocationDto> findAll();

    @Override
    @GetMapping(LocationManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    ResponseEntity<LocationDto> findOne(@PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId);

    @Override
    @GetMapping(LocationManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    ResponseEntity<Set<LocationDto>> findMultiple(@RequestParam(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_IDS) String[] locationIds);

    @Override
    @PostMapping(value = LocationManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<LocationDto> create(@RequestBody LocationDto location);

    @Override
    @PostMapping(value = LocationManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Set<LocationDto>> create(@RequestBody Set<LocationDto> locations);

    @Override
    @DeleteMapping(LocationManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    ResponseEntity<Void> delete(@PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId);

    @GetMapping(LocationManagementApiConstants.MAPPING_GET_MEETINGS_AT_LOCATION)
    @ResponseBody
    ResponseEntity<Set<MeetingDto>> getMeetingsAtLocation(@PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId);

    @GetMapping(LocationManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    ResponseEntity<MeetingDto> getCurrentMeeting(@PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId);

    @PostMapping(LocationManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(LocationManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(LocationManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes);

    @GetMapping(LocationManagementApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}