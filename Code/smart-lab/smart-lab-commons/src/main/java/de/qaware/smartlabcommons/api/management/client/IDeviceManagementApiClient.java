package de.qaware.smartlabcommons.api.management.client;

import de.qaware.smartlabcommons.api.management.DeviceManagementApiConstants;
import de.qaware.smartlabcommons.data.device.IDevice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "device-management", url = "http://localhost:8080")
public interface IDeviceManagementApiClient {

    @GetMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_GET_DEVICES)
    List<IDevice> getDevices();

    @GetMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_GET_DEVICE)
    Optional<IDevice> getDevice(@PathVariable("deviceId") long deviceId);

    @PostMapping(value = DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_CREATE_DEVICE, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createDevice(@RequestBody IDevice device);

    @DeleteMapping(DeviceManagementApiConstants.MAPPING_BASE + DeviceManagementApiConstants.MAPPING_DELETE_DEVICE)
    void deleteDevice(@PathVariable("deviceId") long deviceId);
}
