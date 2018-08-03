package de.qaware.smartlabcore.data.device;

import de.qaware.smartlabcore.data.device.dto.DeviceDto;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceConverter implements IDtoConverter<IDevice, DeviceDto> {

    @Override
    public DeviceDto toDto(IDevice device) {
        return DeviceDto.builder()
                .id(device.getId())
                .type(device.getType())
                .name(device.getName())
                .responsibleDelegate(device.getResponsibleDelegate())
                .build();
    }

    @Override
    public IDevice toEntity(DeviceDto device) {
        return Device.builder()
                .id(device.getId())
                .type(device.getType())
                .name(device.getName())
                .responsibleDelegate(device.getResponsibleDelegate())
                .build();
    }
}
