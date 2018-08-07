package de.qaware.smartlab.api.service.connector.device;

import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.device.DeviceDto;

public interface IDeviceManagementService extends IBasicEntityManagementService<IDevice, DeviceId, DeviceDto> { }
