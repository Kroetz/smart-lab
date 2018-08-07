package de.qaware.smartlab.data.conversion.converter.device;

import de.qaware.smartlab.core.data.device.Device;
import de.qaware.smartlab.core.data.device.DeviceDto;
import de.qaware.smartlab.core.data.device.IDevice;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceConverter implements IDtoConverter<IDevice, DeviceDto> {

    /*
     * TODO: Map fields automatically without breaking the immutability of the entity classes
     * Maybe http://modelmapper.org/ is appropriate for this task
     */

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
