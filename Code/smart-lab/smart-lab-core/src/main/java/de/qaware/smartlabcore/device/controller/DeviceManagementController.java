package de.qaware.smartlabcore.device.controller;

import de.qaware.smartlabcommons.api.DeviceManagementApiConstants;
import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.device.service.IDeviceManagementService;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(DeviceManagementApiConstants.MAPPING_BASE)
@Slf4j
public class DeviceManagementController extends AbstractSmartLabController {

    private final IDeviceManagementService deviceManagementService;

    public DeviceManagementController(IDeviceManagementService deviceManagementService) {
        this.deviceManagementService = deviceManagementService;
    }

    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ALL)
    public Set<IDevice> findAll() {
        return deviceManagementService.findAll();
    }

    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ONE)
    public ResponseEntity<IDevice> findOne(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId) {
        return responseFromOptional(deviceManagementService.findOne(deviceId));
    }

    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_MULTIPLE)
    ResponseEntity<Set<IDevice>> findMultiple(@RequestParam(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_IDS) String[] deviceIds) {
        return responseFromOptionals(deviceManagementService.findMultiple(new HashSet<>(Arrays.asList(deviceIds))));
    }

    @PostMapping(value = DeviceManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody IDevice device) {
        return deviceManagementService.create(device).toResponseEntity();
    }

    @DeleteMapping(DeviceManagementApiConstants.MAPPING_DELETE)
    public ResponseEntity<Void> delete(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId) {
        return deviceManagementService.delete(deviceId).toResponseEntity();
    }
}
