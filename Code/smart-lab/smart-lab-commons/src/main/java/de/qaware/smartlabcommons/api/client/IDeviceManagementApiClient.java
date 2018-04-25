package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.DeviceManagementApiConstants;
import de.qaware.smartlabcommons.api.client.generic.ICrudApiClient;
import de.qaware.smartlabcommons.data.device.IDevice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        value = DeviceManagementApiConstants.FEIGN_CLIENT_VALUE,
        url = DeviceManagementApiConstants.FEIGN_CLIENT_URL)
@Component
public interface IDeviceManagementApiClient extends ICrudApiClient<IDevice> {

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_GET_DEVICES)
    List<IDevice> findAll();

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_GET_DEVICE)
    ResponseEntity<IDevice> findOne(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId);

    @Override
    @PostMapping(
            value = DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_CREATE_DEVICE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> create(@RequestBody IDevice device);

    @Override
    @DeleteMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_DELETE_DEVICE)
    ResponseEntity<Void> delete(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId);
}
