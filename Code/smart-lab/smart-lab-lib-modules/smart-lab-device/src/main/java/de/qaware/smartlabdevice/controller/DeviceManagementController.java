package de.qaware.smartlabdevice.controller;

import de.qaware.smartlabapi.DeviceManagementApiConstants;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.generic.controller.IBasicEntityManagementController;
import de.qaware.smartlabdevice.business.IDeviceManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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
                Arrays.stream(deviceIds)
                        .map(DeviceId::of)
                        .collect(Collectors.toSet())));
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
}
