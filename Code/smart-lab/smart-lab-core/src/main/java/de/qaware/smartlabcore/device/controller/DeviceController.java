package de.qaware.smartlabcore.device.controller;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.device.service.IDeviceService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/smart-lab/api/device")
@Slf4j
public class DeviceController {

    private final IDeviceService deviceService;

    @Autowired
    public DeviceController(@Qualifier("mock") IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public List<IDevice> getDevices() {
        return deviceService.getDevices();
    }

    @GetMapping("/{deviceId}")
    public Optional<IDevice> getDevice(@PathVariable("deviceId") long deviceId) {
        return deviceService.getDevice(deviceId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createDevice(@RequestBody IDevice device) {
        return deviceService.createDevice(device);
    }

    @DeleteMapping("/{deviceId}")
    public void deleteDevice(@PathVariable("deviceId") long deviceId) {
        deviceService.deleteDevice(deviceId);
    }
}
