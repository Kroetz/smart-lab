package de.qaware.smartlabcore.device.controller;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.device.service.IDeviceManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(DeviceManagementController.MAPPING_BASE)
@Slf4j
public class DeviceManagementController {

    public static final String MAPPING_BASE = "/smart-lab/api/device";
    public static final String MAPPING_GET_DEVICES = "";
    public static final String MAPPING_GET_DEVICE = "/{deviceId}";
    public static final String MAPPING_CREATE_DEVICE = "";
    public static final String MAPPING_DELETE_DEVICE = "/{deviceId}";

    private final IDeviceManagementService deviceManagementService;

    public DeviceManagementController(@Qualifier("mock") IDeviceManagementService deviceManagementService) {
        this.deviceManagementService = deviceManagementService;
    }

    @GetMapping(MAPPING_GET_DEVICES)
    public List<IDevice> getDevices() {
        return deviceManagementService.getDevices();
    }

    @GetMapping(MAPPING_GET_DEVICE)
    public Optional<IDevice> getDevice(@PathVariable("deviceId") long deviceId) {
        return deviceManagementService.getDevice(deviceId);
    }

    @PostMapping(value = MAPPING_CREATE_DEVICE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createDevice(@RequestBody IDevice device) {
        return deviceManagementService.createDevice(device);
    }

    @DeleteMapping(MAPPING_DELETE_DEVICE)
    public void deleteDevice(@PathVariable("deviceId") long deviceId) {
        deviceManagementService.deleteDevice(deviceId);
    }
}
