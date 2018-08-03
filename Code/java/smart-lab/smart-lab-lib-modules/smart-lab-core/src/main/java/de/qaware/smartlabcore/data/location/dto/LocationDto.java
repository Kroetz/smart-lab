package de.qaware.smartlabcore.data.location.dto;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.generic.IDto;
import de.qaware.smartlabcore.data.location.LocationId;
import lombok.*;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LocationDto implements IDto {

    private LocationId id;
    private String name;
    private Collection<DeviceId> deviceIds;
}
