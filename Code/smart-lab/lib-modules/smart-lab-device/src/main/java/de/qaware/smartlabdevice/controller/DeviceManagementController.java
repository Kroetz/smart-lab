package de.qaware.smartlabdevice.controller;

import de.qaware.smartlabcommons.api.internal.DeviceManagementApiConstants;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.generic.controller.IEntityManagementController;
import de.qaware.smartlabdevice.business.IDeviceManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(DeviceManagementApiConstants.MAPPING_BASE)
@Slf4j
public class DeviceManagementController extends AbstractSmartLabController implements IEntityManagementController<IDevice> {

    private final IDeviceManagementBusinessLogic deviceManagementBusinessLogic;

    public DeviceManagementController(IDeviceManagementBusinessLogic deviceManagementBusinessLogic) {
        this.deviceManagementBusinessLogic = deviceManagementBusinessLogic;
    }

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ALL)
    public Set<IDevice> findAll() {
        return deviceManagementBusinessLogic.findAll();
    }

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_ONE)
    public ResponseEntity<IDevice> findOne(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId) {
        return responseFromOptional(deviceManagementBusinessLogic.findOne(deviceId));
    }

    @Override
    @GetMapping(DeviceManagementApiConstants.MAPPING_FIND_MULTIPLE)
    public ResponseEntity<Set<IDevice>> findMultiple(@RequestParam(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_IDS) String[] deviceIds) {
        return responseFromOptionals(deviceManagementBusinessLogic.findMultiple(new HashSet<>(Arrays.asList(deviceIds))));
    }

    @Override
    @PostMapping(value = DeviceManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody IDevice device) {
        return deviceManagementBusinessLogic.create(device).toResponseEntity();
    }

    @Override
    @DeleteMapping(DeviceManagementApiConstants.MAPPING_DELETE)
    public ResponseEntity<Void> delete(@PathVariable(DeviceManagementApiConstants.PARAMETER_NAME_DEVICE_ID) String deviceId) {
        return deviceManagementBusinessLogic.delete(deviceId).toResponseEntity();
    }
}
