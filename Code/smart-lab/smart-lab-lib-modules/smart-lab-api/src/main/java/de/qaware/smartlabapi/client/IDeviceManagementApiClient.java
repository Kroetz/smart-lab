package de.qaware.smartlabapi.client;

import de.qaware.smartlabapi.DeviceManagementApiConstants;
import de.qaware.smartlabapi.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@FeignClient(name = DeviceManagementApiConstants.FEIGN_CLIENT_NAME, path = DeviceManagementApiConstants.MAPPING_BASE)
public interface IDeviceManagementApiClient extends IBasicEntityManagementApiClient<IDevice> {

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ALL)
    Set<IDevice> findAll();

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ONE)
    ResponseEntity<IDevice> findOne(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId);

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_MULTIPLE)
    ResponseEntity<Set<IDevice>> findMultiple(@RequestParam(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_IDS) String[] deviceIds);

    @Override
    @PostMapping(
            value = DeviceManagementApiConstants.MAPPING_CREATE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<IDevice> create(@RequestBody IDevice device);

    @Override
    @DeleteMapping(DeviceManagementApiConstants.MAPPING_DELETE)
    ResponseEntity<Void> delete(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId);
}
