package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.DeviceManagementApiConstants;
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
public interface IDeviceManagementApiClient {

    @GetMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_GET_DEVICES)
    List<IDevice> getDevices();

    @GetMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_GET_DEVICE)
    ResponseEntity<IDevice> getDevice(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId);

    @PostMapping(
            value = DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_CREATE_DEVICE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createDevice(@RequestBody IDevice device);

    @DeleteMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_DELETE_DEVICE)
    ResponseEntity<Void> deleteDevice(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId);
}
