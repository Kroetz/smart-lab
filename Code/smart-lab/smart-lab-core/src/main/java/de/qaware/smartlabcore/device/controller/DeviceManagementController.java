package de.qaware.smartlabcore.device.controller;

import de.qaware.smartlabcommons.api.management.DeviceManagementApiConstants;
import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.device.service.IDeviceManagementService;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(DeviceManagementApiConstants.MAPPING_BASE)
@Slf4j
public class DeviceManagementController extends AbstractSmartLabController {

    private final IDeviceManagementService deviceManagementService;

    public DeviceManagementController(IDeviceManagementService deviceManagementService) {
        this.deviceManagementService = deviceManagementService;
    }

    @GetMapping(DeviceManagementApiConstants.MAPPING_GET_DEVICES)
    public List<IDevice> getDevices() {
        return deviceManagementService.getDevices();
    }

    @GetMapping(DeviceManagementApiConstants.MAPPING_GET_DEVICE)
    public ResponseEntity<IDevice> getDevice(@PathVariable("deviceId") long deviceId) {
        return responseFromOptional(deviceManagementService.getDevice(deviceId));
    }

    @PostMapping(value = DeviceManagementApiConstants.MAPPING_CREATE_DEVICE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createDevice(@RequestBody IDevice device) {
        return deviceManagementService.createDevice(device);
    }

    @DeleteMapping(DeviceManagementApiConstants.MAPPING_DELETE_DEVICE)
    public void deleteDevice(@PathVariable("deviceId") long deviceId) {
        deviceManagementService.deleteDevice(deviceId);
    }
}
