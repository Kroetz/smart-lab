package de.qaware.smartlabapi.service.client.device;

import de.qaware.smartlabapi.service.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlabcore.data.device.DeviceDto;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabapi.service.constant.device.DeviceManagementApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

@FeignClient(name = DeviceManagementApiConstants.FEIGN_CLIENT_NAME, path = DeviceManagementApiConstants.MAPPING_BASE)
public interface IDeviceManagementApiClient extends IBasicEntityManagementApiClient<IDevice, DeviceDto> {

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    Set<DeviceDto> findAll();

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    ResponseEntity<DeviceDto> findOne(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId);

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    ResponseEntity<Set<DeviceDto>> findMultiple(@RequestParam(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_IDS) String[] deviceIds);

    @Override
    @PostMapping(
            value = DeviceManagementApiConstants.MAPPING_CREATE_SINGLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<DeviceDto> create(@RequestBody DeviceDto device);

    @Override
    @PostMapping(
            value = DeviceManagementApiConstants.MAPPING_CREATE_MULTIPLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Set<DeviceDto>> create(@RequestBody Set<DeviceDto> devices);

    @Override
    @DeleteMapping(DeviceManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    ResponseEntity<Void> delete(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId);

    @GetMapping(DeviceManagementApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
