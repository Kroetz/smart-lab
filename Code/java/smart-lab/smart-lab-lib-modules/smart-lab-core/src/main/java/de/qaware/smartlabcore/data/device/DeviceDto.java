package de.qaware.smartlabcore.data.device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.generic.IDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDto implements IDto {

    private DeviceId id;
    private String type;
    private String name;
    private String responsibleDelegate;
}
