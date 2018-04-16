package de.qaware.smartlabcommons.api.configprovidermock.client;

import de.qaware.smartlabcommons.api.configprovidermock.DeviceConfigProviderMockApiConstants;
import de.qaware.smartlabcommons.data.device.IDevice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "device-config-provider", url = "http://localhost:8085")
public interface IDeviceConfigProviderMockClient {

    @GetMapping(DeviceConfigProviderMockApiConstants.MAPPING_BASE + DeviceConfigProviderMockApiConstants.MAPPING_GET_DEVICES)
    List<IDevice> getDevices();

    @GetMapping(DeviceConfigProviderMockApiConstants.MAPPING_BASE + DeviceConfigProviderMockApiConstants.MAPPING_GET_DEVICE)
    ResponseEntity<IDevice> getDevice(@PathVariable("deviceId") long deviceId);

    @PostMapping(
            value = DeviceConfigProviderMockApiConstants.MAPPING_BASE + DeviceConfigProviderMockApiConstants.MAPPING_CREATE_DEVICE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createDevice(@RequestBody IDevice device);

    @DeleteMapping(DeviceConfigProviderMockApiConstants.MAPPING_DELETE_DEVICE)
    boolean deleteDevice(@PathVariable("deviceId") long deviceId);
}
