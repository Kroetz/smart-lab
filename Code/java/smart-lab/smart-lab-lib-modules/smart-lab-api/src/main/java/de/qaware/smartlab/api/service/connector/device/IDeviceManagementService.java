package de.qaware.smartlab.api.service.connector.device;

import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.data.device.IDevice;
import de.qaware.smartlab.core.data.device.DeviceDto;

public interface IDeviceManagementService extends IBasicEntityManagementService<IDevice, DeviceId, DeviceDto> { }
