package de.qaware.smartlabcore.data.device;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.generic.IDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DeviceDto implements IDto {

    private DeviceId id;
    private String type;
    private String name;
    private String responsibleDelegate;
}
