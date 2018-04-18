package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.DeviceManagementApiConstants;
import de.qaware.smartlabcommons.data.device.IDevice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "device-management", url = "http://localhost:8080")
@Component
public interface IDeviceManagementApiClient {

    @GetMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_GET_DEVICES)
    List<IDevice> getDevices();

    @GetMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_GET_DEVICE)
    ResponseEntity<IDevice> getDevice(@PathVariable("deviceId") long deviceId);

    @PostMapping(value = DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_CREATE_DEVICE, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createDevice(@RequestBody IDevice device);

    @DeleteMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_DELETE_DEVICE)
    boolean deleteDevice(@PathVariable("deviceId") long deviceId);
}
