package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.DeviceManagementApiConstants;
import de.qaware.smartlabcommons.api.client.generic.IEntityManagementApiClient;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@FeignClient(DeviceManagementApiConstants.FEIGN_CLIENT_VALUE)
@Component
public interface IDeviceManagementApiClient extends IEntityManagementApiClient<IDevice> {

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_FIND_ALL)
    Set<IDevice> findAll();

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_FIND_ONE)
    ResponseEntity<IDevice> findOne(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId);

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_FIND_MULTIPLE)
    ResponseEntity<Set<IDevice>> findMultiple(@RequestParam(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_IDS) String[] deviceIds);

    @Override
    @PostMapping(
            value = DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_CREATE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> create(@RequestBody IDevice device);

    @Override
    @DeleteMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_DELETE)
    ResponseEntity<Void> delete(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId);
}
