package de.qaware.smartlabdeviceconfigprovidermock.controller;

import de.qaware.smartlabcommons.api.configprovidermock.DeviceConfigProviderMockApiConstants;
import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabdeviceconfigprovidermock.service.IDeviceConfigProviderMockService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(DeviceConfigProviderMockApiConstants.MAPPING_BASE)
public class DeviceConfigProviderMockController extends AbstractSmartLabController {

    private final IDeviceConfigProviderMockService deviceConfigProviderService;

    public DeviceConfigProviderMockController(IDeviceConfigProviderMockService deviceConfigProviderService) {
        this.deviceConfigProviderService = deviceConfigProviderService;
    }

    @GetMapping(DeviceConfigProviderMockApiConstants.MAPPING_GET_DEVICES)
    public List<IDevice> getDevices() {
        return deviceConfigProviderService.getDevices();
    }

    @GetMapping(DeviceConfigProviderMockApiConstants.MAPPING_GET_DEVICE)
    public ResponseEntity<IDevice> getDevice(@PathVariable("deviceId") long deviceId) {
        return responseFromOptional(deviceConfigProviderService.getDevice(deviceId));
    }

    @PostMapping(
            value = DeviceConfigProviderMockApiConstants.MAPPING_CREATE_DEVICE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createDevice(@RequestBody IDevice device) {
        return deviceConfigProviderService.createDevice(device);
    }

    @DeleteMapping(DeviceConfigProviderMockApiConstants.MAPPING_DELETE_DEVICE)
    public boolean deleteDevice(@PathVariable("deviceId") long deviceId) {
        return deviceConfigProviderService.deleteDevice(deviceId);
    }
}
