package de.qaware.smartlabdata.converter.device;

import de.qaware.smartlabcore.data.device.Device;
import de.qaware.smartlabcore.data.device.DeviceDto;
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
        return Device.of(
                device.getId(),
                device.getType(),
                device.getName(),
                device.getResponsibleDelegate());
    }
}
