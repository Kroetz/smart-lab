package de.qaware.smartlabcore.device.service.mock;

import de.qaware.smartlabcommons.data.device.IDevice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "device-config-provider", url = "http://localhost:8085")
@ApiIgnore
@RestController
@RequestMapping("/smart-lab/device-config-provider")
@Qualifier("mock")
public interface IDeviceConfigProviderMockClient {

    @PostMapping(value = "/{deviceId}/exists")
    boolean exists(@PathVariable("deviceId") long deviceId);

    @GetMapping
    List<IDevice> getDevices();

    @GetMapping("/{deviceId}")
    Optional<IDevice> getDevice(@PathVariable("deviceId") long deviceId);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createDevice(@RequestBody IDevice device);

    @DeleteMapping("/{deviceId}")
    boolean deleteDevice(@PathVariable("deviceId") long deviceId);
}
