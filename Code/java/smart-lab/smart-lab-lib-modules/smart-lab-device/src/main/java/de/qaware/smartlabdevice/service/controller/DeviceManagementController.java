package de.qaware.smartlabdevice.service.controller;

import de.qaware.smartlabcore.service.constant.device.DeviceManagementApiConstants;
import de.qaware.smartlabcore.data.device.dto.DeviceDto;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
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
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping(DeviceManagementApiConstants.MAPPING_BASE)
@Slf4j
public class DeviceManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<IDevice, DeviceDto> {

    private final IDeviceManagementBusinessLogic deviceManagementBusinessLogic;
    private final IDtoConverter<IDevice, DeviceDto> deviceConverter;

    public DeviceManagementController(
            IDeviceManagementBusinessLogic deviceManagementBusinessLogic,
            IDtoConverter<IDevice, DeviceDto> deviceConverter) {
        this.deviceManagementBusinessLogic = deviceManagementBusinessLogic;
        this.deviceConverter = deviceConverter;
    }

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<DeviceDto> findAll() {
        return this.deviceManagementBusinessLogic.findAll().stream()
                .map(this.deviceConverter::toDto)
                .collect(toSet());
    }

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<DeviceDto> findOne(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId) {
        return responseFromEntityOptional(
                this.deviceManagementBusinessLogic.findOne(DeviceId.of(deviceId)),
                this.deviceConverter);
    }

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    public ResponseEntity<Set<DeviceDto>> findMultiple(@RequestParam(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_IDS) String[] deviceIds) {
        return responseFromEntityOptionals(this.deviceManagementBusinessLogic.findMultiple(stream(deviceIds)
                        .map(DeviceId::of)
                        .collect(toSet())),
                this.deviceConverter);
    }

    @Override
    @PostMapping(value = DeviceManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<DeviceDto> create(@RequestBody DeviceDto device) {
        IDevice deviceToCreate = this.deviceConverter.toEntity(device);
        DeviceDto createdDevice = this.deviceConverter.toDto(this.deviceManagementBusinessLogic.create(deviceToCreate));
        return ResponseEntity.ok(createdDevice);
    }

    @Override
    @PostMapping(value = DeviceManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<DeviceDto>> create(@RequestBody Set<DeviceDto> devices) {
        return ResponseEntity.ok(this.deviceManagementBusinessLogic.create(devices
                .stream()
                .map(this.deviceConverter::toEntity)
                .collect(toSet()))
                .stream()
                .map(this.deviceConverter::toDto)
                .collect(toSet()));
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
