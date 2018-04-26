package de.qaware.smartlabcore.device.controller;

import de.qaware.smartlabcommons.api.DeviceManagementApiConstants;
import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.device.service.IDeviceManagementService;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(DeviceManagementApiConstants.MAPPING_BASE)
@Slf4j
public class DeviceManagementController extends AbstractSmartLabController {

    private final IDeviceManagementService deviceManagementService;

    public DeviceManagementController(IDeviceManagementService deviceManagementService) {
        this.deviceManagementService = deviceManagementService;
    }

    @GetMapping(DeviceManagementApiConstants.MAPPING_GET_DEVICES)
    public Set<IDevice> findAll() {
        return deviceManagementService.findAll();
    }

    @GetMapping(DeviceManagementApiConstants.MAPPING_GET_DEVICE)
    public ResponseEntity<IDevice> findOne(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId) {
        return responseFromOptional(deviceManagementService.findOne(deviceId));
    }

    @PostMapping(value = DeviceManagementApiConstants.MAPPING_CREATE_DEVICE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody IDevice device) {
        return deviceManagementService.create(device).toResponseEntity();
    }

    @DeleteMapping(DeviceManagementApiConstants.MAPPING_DELETE_DEVICE)
    public ResponseEntity<Void> delete(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId) {
        return deviceManagementService.delete(deviceId).toResponseEntity();
    }
}
