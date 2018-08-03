package de.qaware.smartlabapi.service.connector.device;

import de.qaware.smartlabapi.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlabcore.data.device.dto.DeviceDto;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;

public interface IDeviceManagementService extends IBasicEntityManagementService<IDevice, DeviceId, DeviceDto> { }
