package de.qaware.smartlabdevice.service.controller;

import de.qaware.smartlabapi.service.constant.device.DeviceManagementApiConstants;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.service.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.service.controller.IBasicEntityManagementController;
import de.qaware.smartlabcore.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.service.url.IBaseUrlDetector;
import de.qaware.smartlabdevice.service.business.IDeviceManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping(DeviceManagementApiConstants.MAPPING_BASE)
@Slf4j
public class DeviceManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<IDevice> {

    private final IDeviceManagementBusinessLogic deviceManagementBusinessLogic;

    public DeviceManagementController(IDeviceManagementBusinessLogic deviceManagementBusinessLogic) {
        this.deviceManagementBusinessLogic = deviceManagementBusinessLogic;
    }

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<IDevice> findAll() {
        return this.deviceManagementBusinessLogic.findAll();
    }

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<IDevice> findOne(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId) {
        return responseFromOptional(this.deviceManagementBusinessLogic.findOne(DeviceId.of(deviceId)));
    }

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    public ResponseEntity<Set<IDevice>> findMultiple(@RequestParam(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_IDS) String[] deviceIds) {
        return responseFromOptionals(this.deviceManagementBusinessLogic.findMultiple(
                stream(deviceIds)
                        .map(DeviceId::of)
                        .collect(toSet())));
    }

    @Override
    @PostMapping(value = DeviceManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<IDevice> create(@RequestBody IDevice device) {
        return ResponseEntity.ok(this.deviceManagementBusinessLogic.create(device));
    }

    @Override
    @PostMapping(value = DeviceManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<IDevice>> create(@RequestBody Set<IDevice> devices) {
        return ResponseEntity.ok(this.deviceManagementBusinessLogic.create(devices));
    }

    @Override
    @DeleteMapping(DeviceManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId) {
        return this.deviceManagementBusinessLogic.delete(DeviceId.of(deviceId)).toResponseEntity();
    }

    @RestController
    @RequestMapping(DeviceManagementApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(DeviceManagementApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
