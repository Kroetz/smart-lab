package de.qaware.smartlabdeviceconfigprovidermock.controller;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabdeviceconfigprovidermock.service.IDeviceConfigProviderMockService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/smart-lab/device-config-provider")
public class DeviceConfigProviderMockController {

    private final IDeviceConfigProviderMockService deviceConfigProviderService;

    public DeviceConfigProviderMockController(IDeviceConfigProviderMockService deviceConfigProviderService) {
        this.deviceConfigProviderService = deviceConfigProviderService;
    }

    @PostMapping("/{deviceId}/exists")
    public boolean exists(@PathVariable("deviceId") long deviceId) {
        return deviceConfigProviderService.exists(deviceId);
    }

    @GetMapping
    public List<IDevice> getDevices() {
        return deviceConfigProviderService.getDevices();
    }

    @GetMapping("/{deviceId}")
    public Optional<IDevice> getDevice(@PathVariable("deviceId") long deviceId) {
        return deviceConfigProviderService.getDevice(deviceId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createDevice(@RequestBody IDevice device) {
        return deviceConfigProviderService.createDevice(device);
    }

    @DeleteMapping("/{deviceId}")
    public boolean deleteDevice(@PathVariable("deviceId") long deviceId) {
        return deviceConfigProviderService.deleteDevice(deviceId);
    }
}
